package com.piapps.flashcardpro.core.util

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import com.piapps.flashcardpro.R
import java.io.*
import java.util.*

object FileUtils {

    val MAX_FILE_SIZE = 125000000 // 125 * 1000 * 1000

    fun cacheDir(ctx: Context) = ctx.cacheDir

    fun cameraCacheDir(ctx: Context) = cacheDir(ctx)

    fun convertToFile(context: Context, bitmap: Bitmap): File? {
        //create a file to write bitmap data
        val f = File(context.cacheDir, System.currentTimeMillis().toString() + "")
        try {
            f.createNewFile()
            //Convert bitmap to byte array
            val bos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50 /*ignored for PNG*/, bos)
            val bitmapdata = bos.toByteArray()
            //write the bytes in file
            val fos = FileOutputStream(f)
            fos.write(bitmapdata)
            fos.flush()
            fos.close()

            return f
        } catch (e: Throwable) {
            e.printStackTrace()
        }

        return null
    }

    fun getMinutes(time: String?): Long {
        if (time == null || time.isEmpty() || time.isBlank())
            return 0
        val l = java.lang.Long.valueOf(time)!!
        return l / 60000
    }

    fun getSeconds(time: String?): Long {
        if (time == null || time.isEmpty() || time.isBlank())
            return 0
        val l = java.lang.Long.valueOf(time)!!
        return l % 60000 / 1000
    }

    fun getSize(size: String?): String {
        if (size == null || size.isEmpty() || size.isBlank())
            return ""
        val l = java.lang.Long.valueOf(size)!!
        return (l / (1024 * 1024)).toString()
    }

    fun getPrettyVideoDuration(time: String?): String {
        if (time == null) return "0:00"
        return if (getSeconds(time) < 10)
            getMinutes(time).toString() + ":0" + getSeconds(
                time
            )
        else
            getMinutes(time).toString() + ":" + getSeconds(
                time
            )
    }

    fun humanReadableByteCount(bytes: Long): String {
        val unit = 1024
        if (bytes < unit) return bytes.toString() + " B"
        val exp = (Math.log(bytes.toDouble()) / Math.log(unit.toDouble())).toInt()
        val pre = "kMGTPE"[exp - 1] + ""
        return String.format("%.1f %sB", bytes / Math.pow(unit.toDouble(), exp.toDouble()), pre)
    }

    fun humanReadableByteCount(strbytes: String): String {
        var bytes: Long = 0
        try {
            bytes = java.lang.Long.parseLong(strbytes)
        } catch (e: Exception) {
            return ""
        }

        val unit = 1024
        if (bytes < unit) return bytes.toString() + " B"
        val exp = (Math.log(bytes.toDouble()) / Math.log(unit.toDouble())).toInt()
        val pre = "kMGTPE"[exp - 1] + ""
        return String.format("%.1f %sB", bytes / Math.pow(unit.toDouble(), exp.toDouble()), pre)
    }

    fun fetchPaths(context: Context?, uri: Uri): ArrayList<String> {
        var column_index = 0
        val listOfAllImages = arrayListOf<String>()
        var absolutePathOfImage = ""
        val projection = arrayOf(MediaStore.MediaColumns.DATA)

        if (context != null) {
            val cursor = context.getContentResolver().query(
                uri, projection, null,
                null, null
            )
            if (cursor == null) return listOfAllImages
            column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA)
            while (cursor.moveToNext()) {
                absolutePathOfImage = cursor.getString(column_index)
                listOfAllImages.add(absolutePathOfImage)
            }
            listOfAllImages.reverse()
            cursor.close()
        }
        return listOfAllImages
    }

    fun copyInputStreamToFile(inputStream: InputStream, target: File) {
        inputStream.use { input ->
            target.outputStream().use { fileOut ->
                input.copyTo(fileOut)
            }
        }
    }

    fun writeFileFromIS(file: File?, `is`: InputStream?): Boolean {
        if (`is` == null) return false
        var os: OutputStream? = null
        try {
            os = BufferedOutputStream(FileOutputStream(file!!, true))
            val data = ByteArray(8196)
            var len = `is`.read(data, 0, 8196)

            while (len != -1) {
                os.write(data, 0, len)
                len = `is`.read(data, 0, 8196)
            }
            return true
        } catch (e: IOException) {
            e.printStackTrace()
            return false
        } finally {
            `is`.close()
            os?.close()
        }
    }


    fun getFileIcon(filename: String): Int {
        if (filename.endsWith(".au"))
            return R.drawable.extension_audition

        if (filename.endsWith(".avi"))
            return R.drawable.extension_avi

        if (filename.endsWith(".css"))
            return R.drawable.extension_css

        if (filename.endsWith(".csv"))
            return R.drawable.extension_csv

        if (filename.endsWith(".doc") || filename.endsWith(".docx"))
            return R.drawable.extension_doc

        if (filename.endsWith(".exe"))
            return R.drawable.extension_exe

        if (filename.endsWith(".fla"))
            return R.drawable.extension_fla

        if (filename.endsWith(".html"))
            return R.drawable.extension_html

        if (filename.endsWith(".ai"))
            return R.drawable.extension_illustrator

        if (filename.endsWith(".js"))
            return R.drawable.extension_json

        if (filename.endsWith(".jpg"))
            return R.drawable.extension_jpg

        if (filename.endsWith(".json"))
            return R.drawable.extension_json

        if (filename.endsWith(".mp3"))
            return R.drawable.extension_mp3

        if (filename.endsWith(".mp4"))
            return R.drawable.extension_mp4

        if (filename.endsWith(".pdf"))
            return R.drawable.extension_pdf

        if (filename.endsWith(".ps"))
            return R.drawable.extension_photoshop

        if (filename.endsWith(".png"))
            return R.drawable.extension_png

        if (filename.endsWith(".ppt") || filename.endsWith(".pptx"))
            return R.drawable.extension_ppt

        if (filename.endsWith(".psd"))
            return R.drawable.extension_psd

        if (filename.endsWith(".rtf"))
            return R.drawable.extension_rtf

        if (filename.endsWith(".svg"))
            return R.drawable.extension_svg

        if (filename.endsWith(".txt"))
            return R.drawable.extension_txt

        if (filename.endsWith(".xls"))
            return R.drawable.extension_xls

        if (filename.endsWith(".xml"))
            return R.drawable.extension_xml

        if (filename.endsWith(".zip") || filename.endsWith(".rar"))
            return R.drawable.extension_zip

        return R.drawable.extension_file
    }

    fun getFileIcon(file: File): Int {
        if (file.isDirectory) {
            return R.drawable.extension_folder
        }
        return getFileIcon(file.name)
    }
}
