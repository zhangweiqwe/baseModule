package cn.wsgwz.basemodule.utilities.manager

import android.os.Environment
import cn.wsgwz.basemodule.BaseApplication
import java.io.*

class FileManager private constructor() {


    fun saveFile(file: File, outputFile: File, append: Boolean = false) {
        if (!file.exists()) {
            throw Exception("nonsupport")
        }
        return saveFile(FileInputStream(file), outputFile, append)
    }


    fun saveLog(string: String, append: Boolean = false) {
        saveFile(string, ERROR_LOG_FILE, append)
    }

    private fun saveFile(string: String, outputFile: File, append: Boolean = false) {
        saveFile(ByteArrayInputStream(string.toByteArray()), outputFile, append)
    }

    fun saveFile(
        inputStream: InputStream,
        outputFile: File,
        append: Boolean = false
    ) {
        val buffer = ByteArray(1024)
        var len: Int

        val fileOutputStream = FileOutputStream(outputFile, append)
        while (true) {
            len = inputStream.read(buffer)

            if (len != -1) {
                fileOutputStream.write(buffer, 0, len)
            } else {
                break
            }
        }

        fileOutputStream.flush()
        fileOutputStream.close()
        inputStream.close()
    }


    /*fun saveFile(context: Context, inputStream: InputStream, fileName: String) {
        return saveFile(inputStream, File(context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), fileName))
    }*/


    companion object {
        val ERROR_LOG_FILE = File(
            BaseApplication.getInstance().getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS),
              "errorLog.txt"
        )

        const val UPDATE_FILE_NAME = "update.apk"


        // For Singleton instantiation
        @Volatile
        private var instance: FileManager? = null

        fun getInstance() =
            instance ?: synchronized(this) {
                instance ?: FileManager().also { instance = it }
            }
    }
}