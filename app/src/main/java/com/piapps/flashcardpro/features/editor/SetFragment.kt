package com.piapps.flashcardpro.features.editor

import android.Manifest
import android.content.Context
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.kent.layouts.setIconColor
import com.piapps.flashcardpro.R
import com.piapps.flashcardpro.core.db.tables.CardDb
import com.piapps.flashcardpro.core.extension.*
import com.piapps.flashcardpro.core.platform.BaseFragment
import com.piapps.flashcardpro.core.platform.component.bottom.BottomMenu
import com.piapps.flashcardpro.core.platform.component.bottom.BottomMenuFragment
import com.piapps.flashcardpro.core.platform.component.bottom.BottomMenuItem
import com.piapps.flashcardpro.core.platform.component.bottom.OnBottomMenuClickListener
import com.piapps.flashcardpro.core.platform.component.menu.Menu
import com.piapps.flashcardpro.core.platform.component.menu.MenuItem
import com.piapps.flashcardpro.core.util.PermissionUtils
import com.piapps.flashcardpro.features.MainActivity
import com.piapps.flashcardpro.features.dialog.DialogFragment
import com.piapps.flashcardpro.features.editor.SetEditorView.Companion.CARD_BACK_COLOR
import com.piapps.flashcardpro.features.editor.SetEditorView.Companion.CARD_DRAWING
import com.piapps.flashcardpro.features.editor.SetEditorView.Companion.CARD_IMAGE
import com.piapps.flashcardpro.features.editor.SetEditorView.Companion.CARD_TEXT
import com.piapps.flashcardpro.features.editor.SetEditorView.Companion.CARD_TEXT_COLOR
import com.piapps.flashcardpro.features.editor.SetEditorView.Companion.EXPORT
import com.piapps.flashcardpro.features.editor.SetEditorView.Companion.IMPORT
import com.piapps.flashcardpro.features.editor.SetEditorView.Companion.REVERSE
import com.piapps.flashcardpro.features.editor.SetEditorView.Companion.STATS
import com.piapps.flashcardpro.features.editor.adapter.CardsEditorAdapter
import com.piapps.flashcardpro.features.editor.adapter.LabelsAdapter
import com.piapps.flashcardpro.features.files.view.FilesFragment
import com.piapps.flashcardpro.features.files.view.OnFilesSelectedListener
import com.piapps.flashcardpro.features.labels.LabelsFragment
import com.piapps.flashcardpro.features.quiz.QuizFragment
import com.piapps.flashcardpro.features.stats.StatsFragment
import com.piapps.flashcardpro.features.study.StudyFragment
import java.io.File

/**
 * Created by abduaziz on 2019-09-30 at 21:32.
 */

class SetFragment : BaseFragment(), SetEditorView,
    EditNameFragment.OnEditNameListener,
    CardsEditorAdapter.OnCardClickListener,
    ColorFragment.OnColorSelectedListener,
    EditCardTextFragment.OnCardTextEditedListener,
    PhotosFragment.OnCardImageSelectedListener,
    DrawFragment.OnDrawingListener,
    LabelsFragment.OnLabelsEditListener,
    OnFilesSelectedListener,
    QuizFragment.OnCardsUpdatedListener,
    StudyFragment.OnSetStudyDurationUpdatedListener,
    OnBottomMenuClickListener {

    companion object {
        fun newSet(): SetFragment = set(1)

        fun set(setId: Long): SetFragment {
            val fragment = SetFragment()
            fragment.arguments = Bundle().apply {
                putLong("id", setId)
            }
            return fragment
        }
    }

    override fun create() {
        enterAnimation = ENTER_FROM_RIGHT
        exitAnimation = EXIT_TO_RIGHT
        super.create()
    }

    lateinit var presenter: SetPresenter

    val adapter: CardsEditorAdapter = CardsEditorAdapter()
    var onSetUpdatedListener: OnSetUpdatedListener? = null
    var snapHelper = LinearSnapHelper()
    lateinit var layoutManager: LinearLayoutManager

    lateinit var ivSetColor: AppCompatImageView
    lateinit var rv: RecyclerView
    lateinit var ivPrevious: AppCompatImageView
    lateinit var ivNext: AppCompatImageView
    lateinit var rvLabels: RecyclerView
    lateinit var ivAdd: AppCompatImageView
    lateinit var ivBottomMenu: AppCompatImageView
    lateinit var ivStudy: AppCompatImageView
    lateinit var ivQuiz: AppCompatImageView
    lateinit var tvCurrentCard: TextView
        
    var currentCardPosition = 0

    override fun createView(context: Context) = UI()

    override fun createMenu(): Menu? {
        return Menu().apply {
            addMenu(R.string.edit_set_name, ctx.getLocalizedString(R.string.edit_set_name))
            addMenu(R.string.add_labels, ctx.getLocalizedString(R.string.add_labels))
            addMenu(R.string.move_to_trash, ctx.getLocalizedString(R.string.move_to_trash))
        }
    }

    override fun viewCreated(view: View?, args: Bundle?) {
        super.viewCreated(view, args)
        presenter = SetPresenter(this)
        appComponent.inject(presenter)
        adapter.onCardClickListener = this

        val id = args?.getLong("id", 1L) ?: 1L
        presenter.loadSetDetails(id)

        actionBar?.tvTitle?.setOnClickListener {
            showSetTitleEditor(presenter.set.title)
        }

        ivAdd.setOnClickListener {
            presenter.addNewCard()
        }

        ivSetColor.setOnClickListener {
            (activity as MainActivity).openFragment(ColorFragment.setColor().apply {
                onColorSelectedListener = this@SetFragment
            }, true)
        }

        ivStudy.setOnClickListener {
            adapter.updateOrders()
            presenter.autoSave(adapter.list)
            (activity as MainActivity).openFragment(StudyFragment.set(presenter.set.id).apply {
                onSetStudyDurationUpdatedListener = this@SetFragment
            }, true)
        }

        ivQuiz.setOnClickListener {
            adapter.updateOrders()
            presenter.autoSave(adapter.list)
            (activity as MainActivity).openFragment(QuizFragment.set(presenter.set.id).apply {
                onCardsUpdatedListener = this@SetFragment
            }, true)
        }
        
        ivPrevious.setOnClickListener {
            scrollPrevious()
        }

        ivNext.setOnClickListener {
            scrollNext()
        }
    }

    override fun resume() {
        if (adapter.list.isEmpty())
            presenter.loadCards()
    }

    override fun paused() {
        adapter.notifyDataSetChanged()
    }

    override fun menuClicked(item: MenuItem) {
        when (item.id) {
            R.string.edit_set_name -> {
                showSetTitleEditor(presenter.set.title)
            }
            R.string.add_labels -> {
                (activity as MainActivity).openFragment(LabelsFragment.forLabels(presenter.set.labels).apply {
                    onLabelsEditListener = this@SetFragment
                    onLabelsEditListener
                }, true)
            }
            R.string.move_to_trash -> {
                val dialog = ctx.alert {
                    setMessage(ctx.getLocalizedString(R.string.are_you_sure_to_move_to_trash))
                }
                dialog.setButton(DialogInterface.BUTTON_NEGATIVE, ctx.getLocalizedString(R.string.no)) { d, i ->
                    dialog.dismiss()
                }
                dialog.setButton(DialogInterface.BUTTON_POSITIVE, ctx.getLocalizedString(R.string.yes)) { d, i ->
                    presenter.moveSetToTrash()
                    onSetUpdatedListener?.onSetMovedToTrash(presenter.set)
                    onSetUpdatedListener = null
                    dialog.dismiss()
                    close()
                }
                dialog.show()
            }
        }
    }

    fun showMoreOptions() {
        val menu = BottomMenuFragment("", BottomMenu().apply {
            addMenu(STATS, ctx.getLocalizedString(R.string.statistics), R.drawable.ic_stats)
            addMenu(REVERSE, ctx.getLocalizedString(R.string.reverse_cards), R.drawable.ic_flip)
            addMenu(EXPORT, ctx.getLocalizedString(R.string.export_to_csv), R.drawable.ic_export)
            addMenu(IMPORT, ctx.getLocalizedString(R.string.import_from_csv), R.drawable.ic_import)
        })
        menu.onBottomMenuClickListener = this
        (activity as MainActivity).openFragment(menu, true)
    }

    override fun onCardEditClick() {
        val card = adapter.list.getOrNull(layoutManager.findFirstCompletelyVisibleItemPosition())
        if (card == null) return
        presenter.editingCard = card
        val menu = BottomMenu().apply {
            addMenu(CARD_TEXT, ctx.getLocalizedString(R.string.edit_text), R.drawable.ic_text)
            addMenu(CARD_IMAGE, ctx.getLocalizedString(R.string.add_image), R.drawable.ic_image)
            addMenu(CARD_DRAWING, ctx.getLocalizedString(R.string.drawing), R.drawable.ic_brush)
            addMenu(CARD_TEXT_COLOR, ctx.getLocalizedString(R.string.choose_card_text_color), R.drawable.ic_color)
            addMenu(
                CARD_BACK_COLOR,
                ctx.getLocalizedString(R.string.choose_card_background_color),
                R.drawable.ic_text_color
            )
        }
        val fragment = BottomMenuFragment("", menu)
        fragment.onBottomMenuClickListener = this
        (activity as MainActivity).openFragment(fragment, true)
    }

    override fun bottomMenuClick(item: BottomMenuItem) {
        when (item.id) {
            STATS -> {
                (activity as MainActivity).replaceFragment(StatsFragment.set(presenter.set.id), true)
            }
            REVERSE -> {
                (activity as MainActivity).closeBottomMenu()
                adapter.reverseCards()
                presenter.autoSave(adapter.list)
            }
            EXPORT -> {
                (activity as MainActivity).closeBottomMenu()
                checkAndExportToCSV()
            }
            IMPORT -> {
                (activity as MainActivity).replaceFragment(FilesFragment().apply {
                    onFilesSelectedListener = this@SetFragment
                }, true)
            }
            CARD_TEXT -> {
                (activity as MainActivity).closeBottomMenu(false)
                onCardEditTextClick()
            }
            CARD_TEXT_COLOR -> {
                (activity as MainActivity).replaceFragment(ColorFragment.cardTextColor().apply {
                    onColorSelectedListener = this@SetFragment
                }, true)
            }
            CARD_IMAGE -> {
                (activity as MainActivity).replaceFragment(PhotosFragment().apply {
                    onCardImageSelectedListener = this@SetFragment
                }, true)
            }
            CARD_DRAWING -> {
                (activity as MainActivity).replaceFragment(DrawFragment().apply {
                    onDrawingListener = this@SetFragment
                }, true)
            }
            CARD_BACK_COLOR -> {
                (activity as MainActivity).replaceFragment(ColorFragment.cardBackground().apply {
                    onColorSelectedListener = this@SetFragment
                }, true)
            }
        }
    }

    override fun setTitle(s: String) {
        actionBar?.setTitle(if (s.isNotBlank()) s else ctx.getLocalizedString(R.string.untitled_set))
    }

    override fun setSetColor(c: Int) {
        ivSetColor.setIconColor(ctx, c)
    }

    override fun setSetColor(cs: String) {
        ivSetColor.setIconColor(cs.toColor())
        adapter.defaultColor = cs
    }

    override fun showSetTitleEditor(current: String) {
        val fragment = EditNameFragment.current(current)
        fragment.onEditNameListener = this
        (activity as MainActivity).openFragment(fragment, true)
    }

    override fun onEditName(s: String) {
        if (s.isBlank()) return
        setTitle(s)
        presenter.editSetName(s)
    }

    override fun showLabels(labels: String) {
        rvLabels.adapter = LabelsAdapter(labels.split(",").filter { it.isNotBlank() })
    }

    override fun onLabelsEdited(labels: String) {
        showLabels(labels)
        presenter.saveLabels(labels)
    }

    override fun onSetColorSelected(color: Int) {
        ivSetColor.setIconColor(ctx, color)
        presenter.setDefaultColor(ContextCompat.getColor(ctx, color).toHexColor())
        adapter.defaultColor = ContextCompat.getColor(ctx, color).toHexColor()
        adapter.notifyDataSetChanged()
    }

    override fun showCards(cards: List<CardDb>) {
        adapter.addAll(cards)
    }

    override fun showNewCard(card: CardDb) {
        var pos = layoutManager.findFirstCompletelyVisibleItemPosition()
        if (pos < 0) pos = 0
        adapter.addNewCard(card, pos)
        rv.smoothScrollToPosition(pos)
    }

    override fun onCardDeleteClick() {
        val card = adapter.list.getOrNull(layoutManager.findFirstCompletelyVisibleItemPosition())
        if (card == null) return
        adapter.remove(card)
        presenter.deleteCardOffline(card)
        showCurrentCardPosition()
    }

    override fun onCardEditTextClick() {
        val card = adapter.list.getOrNull(layoutManager.findFirstCompletelyVisibleItemPosition())
        if (card == null) return
        presenter.editingCard = card
        (activity as MainActivity).openFragment(
            EditCardTextFragment.withText(
                if (card.isEditingBack) card.back else card.front
            ).apply { onCardTextEditedListener = this@SetFragment }, true
        )
    }

    override fun onCardTextEdited(text: String) {
        presenter.editCardText(text)
        adapter.updateCard(presenter.editingCard)
    }

    override fun onCardBackgroundColorSelected(color: Int) {
        presenter.editCardBackgroundColor(ContextCompat.getColor(ctx, color).toHexColor())
        adapter.updateCard(presenter.editingCard)
    }

    override fun onCardTextColorSelected(color: Int) {
        presenter.editCardTextColor(ContextCompat.getColor(ctx, color).toHexColor())
        adapter.updateCard(presenter.editingCard)
    }

    override fun onCardBackgroundImageSelected(path: String) {
        presenter.editCardBackgroundImage(path)
        adapter.updateCard(presenter.editingCard)
    }

    override fun onDrawingImageSelected(path: String) {
        presenter.editCardBackgroundImage(path)
        adapter.updateCard(presenter.editingCard)
    }

    fun checkAndExportToCSV() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(
                ctx, Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            activity?.requestPermissions(
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                PermissionUtils.WRITE_EXTERNAL_STORAGE
            )
            return
        }
        presenter.exportCSV()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (PermissionUtils.permissionGranted(requestCode, grantResults, PermissionUtils.WRITE_EXTERNAL_STORAGE)) {
            checkAndExportToCSV()
        }
    }

    override fun showSetExported(path: String) {
        //toast(path)
        ///toast(R.string.done)
        (activity as MainActivity).openFragment(
            DialogFragment.dialog(R.drawable.ic_check, R.string.csv_export_info),
            true
        )
    }

    override fun onFilesSelected(files: ArrayList<File>) {
        files.forEach {
            presenter.importCSV(it)
        }
    }

    override fun onCardsRatingUpdated(list: List<CardDb>) {
        list.forEach {
            adapter.updateCard(it)
        }
    }

    override fun onSetStudiedDuration(duration: Long) {
        presenter.set.lastStudyDuration = presenter.set.lastStudyDuration + duration
    }

    override fun showCurrentCardPosition() {
        val pos = layoutManager.findFirstCompletelyVisibleItemPosition()
        if (pos != -1)
            currentCardPosition = pos
        tvCurrentCard.text = "${currentCardPosition % adapter.list.size + 1} / ${adapter.list.size}"
    }

    override fun showToast(res: Int) {
        toast(res)
    }

    override fun removed() {
        adapter.updateOrders()
        presenter.autoSave(adapter.list)
        if (!presenter.set.isTrash)
            onSetUpdatedListener?.onSetUpdated(presenter.set)
        presenter.clear()
        super.removed()
    }
        
    fun scrollNext() {
        // if the number of cards is 1, no need to scroll
        if (adapter.list.size == 1) return
        currentCardPosition += 1
        validateCurrentCardPosition()
        rv.smoothScrollToPosition(currentCardPosition)
        showCurrentCardPosition()
    }

    fun scrollPrevious() {
        // if the number of cards is 1, no need to scroll
        if (adapter.list.size == 1) return
        currentCardPosition -= 1
        validateCurrentCardPosition()
        rv.smoothScrollToPosition(currentCardPosition)
        showCurrentCardPosition()
    }
        
    fun validateCurrentCardPosition() {
        // Noone is gonna scroll Int.MAX_VALUE / 2 times (billion times)
        // Validate against if some dude scrolls billion times (happens with very low probability)
        if (currentCardPosition == Int.MAX_VALUE)
            currentCardPosition -= 1
        else if (currentCardPosition == -1)
            currentCardPosition = 0
    }
}
