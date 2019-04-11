package com.example.voiceassistant.MainActivityFragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.example.voiceassistant.R
import com.example.voiceassistant.Retrofit.RetrofitClient
import com.example.voiceassistant.Util.VoiceFilterUtils
import java.util.*

class VoiceAssistanceFragment : Fragment(){

    companion object {
        fun newInstance(): VoiceAssistanceFragment = VoiceAssistanceFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.voice_assistance_fragment, container, false)

        val voiceButton = view.findViewById<Button>(R.id.voiceButton)
        voiceButton.setOnClickListener {
            startVoiceSpeech()
        }

        return view
    }

    private fun startVoiceSpeech(){
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en-US")

        if(intent.resolveActivity(activity?.packageManager) != null ){
            startActivityForResult(intent, 200)

        }else{
            Toast.makeText(activity, "Voice Recognition not supported", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when(requestCode){
            200 -> {
                if (resultCode == Activity.RESULT_OK && data != null) {
                    val result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                    Log.d(RetrofitClient.TAG,result[0].toString())
                    val cityId = VoiceFilterUtils.getIdByCity(result[0].split(' '))
                    if(cityId != -1){
                        RetrofitClient.getWeatherById(cityId)
                    }
                }
            }
        }
    }
}