package cn.wsgwz.basemodule

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.core.content.FileProvider
import cn.wsgwz.basemodule.utilities.manager.FileManager

class TestToolSettingActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.BaseAppTheme)
        super.onCreate(savedInstanceState)
        /*setContentView(FrameLayout(this).also {
            it.id = it.hashCode()
            supportFragmentManager.beginTransaction().replace(it.id, TestToolSettingPreferenceFragment()).commit()
        })*/


        supportFragmentManager.beginTransaction().replace(android.R.id.content, TestToolSettingPreferenceFragment())
                .commit()
        supportActionBar?.also {
            it.setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.test_tool_setting, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            R.id.share_error_log -> {
                shareErrorLog(this)
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }


    private fun shareErrorLog(context: Context) {
        if (!FileManager.ERROR_LOG_FILE.exists()) {
            Toast.makeText(context, context.getString(R.string.file_does_not_exist), Toast.LENGTH_SHORT).show()
            return
        }
        val intent = Intent(Intent.ACTION_SEND)//发送多个文件
        intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
        intent.type = "*/*"
        intent.putExtra(
                Intent.EXTRA_STREAM,
                FileProvider.getUriForFile(context, BaseConst.FILE_PROVIDER, FileManager.ERROR_LOG_FILE)
        )
        context.startActivity(intent)

//        val files = ArrayList<Uri>()
//        files.add(FileProvider.getUriForFile(context, BaseConst.FILE_PROVIDER, FileManager.ERROR_LOG_FILE))
//        val intent = Intent(Intent.ACTION_SEND_MULTIPLE)//发送多个文件
//        intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION;
//        intent.type = "*/*"//多个文件格式
//        intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, files)//Intent.EXTRA_STREAM同于传输文件流
//        context.startActivity(intent)
    }




    override fun onResume() {
        super.onResume()
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            enterPicInPic()
        }*/
    }

    /*@RequiresApi(Build.VERSION_CODES.O)
    private fun enterPicInPic() {
        val builder = PictureInPictureParams.Builder();
        // 设置宽高比例值，第一个参数表示分子，第二个参数表示分母
        // 下面的10/5=2，表示画中画窗口的宽度是高度的两倍
        val aspectRatio = Rational(10, 5);
        // 设置画中画窗口的宽高比例
        builder.setAspectRatio(aspectRatio);
        // 进入画中画模式，注意enterPictureInPictureMode是Android8.0之后新增的方法
        enterPictureInPictureMode(builder.build());
    }*/


}
