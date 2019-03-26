package cn.wsgwz.basemodule

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.preference.*
import cn.wsgwz.basemodule.widgets.suspension.SuspensionWindowManager
import cn.wsgwz.basemodule.widgets.suspension.SuspensionWindowType

class TestToolSettingPreferenceFragment : PreferenceFragmentCompat(), Preference.OnPreferenceChangeListener {

    private val suspensionWindowManager = SuspensionWindowManager.getInstance()
    /*override fun onCreate(savedInstanceState: Bundle?) {
        BaseApplication.getPreferences().also {
            if (it.getString("base_url", null) == null) {
                it.edit().putString("base_url", BaseApplication.getInstance().defaultBaseUrl).apply()
            }
        }
        super.onCreate(savedInstanceState)
    }*/

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (findPreference("is_show_network_data_suspension_window") as CheckBoxPreference).also {
            it.onPreferenceChangeListener = this
        }

        (findPreference("base_url") as ListPreference).also {
            it.onPreferenceChangeListener = this
            it.summary = BaseApplication.getInstance().baseUrl
        }
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences_test_tool_setting, rootKey)
    }

    override fun onPreferenceChange(preference: Preference, newValue: Any): Boolean {
        return when (preference.key) {
            "is_show_network_data_suspension_window" -> {
                if (newValue as Boolean) {
                    context?.also {
                        suspensionWindowManager.showWithRequestPermission(it, SuspensionWindowType.LOG)
                    }

                } else {
                    suspensionWindowManager.hide(SuspensionWindowType.LOG)
                }
                true
            }


            "base_url" -> {
                (newValue as String).also { base_url ->
                    Toast.makeText(context, getString(R.string.restart_takes_effect), Toast.LENGTH_SHORT).show()
                    (findPreference(preference.key) as ListPreference).also { listPreference ->
                        listPreference.summary = base_url
                    }
                }
                true
            }
            else -> {
                false
            }
        }
    }


}