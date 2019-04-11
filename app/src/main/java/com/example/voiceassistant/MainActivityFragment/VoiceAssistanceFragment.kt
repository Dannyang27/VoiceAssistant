package com.example.voiceassistant.MainActivityFragment

import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.voiceassistant.R
import com.example.voiceassistant.Retrofit.RetrofitClient

class VoiceAssistanceFragment : Fragment(), RecognitionListener{

    companion object {
        fun newInstance(): VoiceAssistanceFragment = VoiceAssistanceFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.voice_assistance_fragment, container, false)

        val voiceButton = view.findViewById<Button>(R.id.voiceButton)
        val speechRecognizer = SpeechRecognizer.createSpeechRecognizer(activity)
        speechRecognizer.setRecognitionListener(this)

        val recognizerIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, "en_US")
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en_US")
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true)

        voiceButton.setOnClickListener {
            speechRecognizer.startListening(recognizerIntent)
        }

        return view
    }

    override fun onReadyForSpeech(params: Bundle?) {
        Log.d(RetrofitClient.TAG, "onReadyForSpeech")
    }

    override fun onRmsChanged(rmsdB: Float) {
    }

    override fun onBufferReceived(buffer: ByteArray?) {
    }

    override fun onPartialResults(partialResults: Bundle?) {
        val partialText = partialResults?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)

        Log.d(RetrofitClient.TAG, partialText.toString())
    }

    override fun onEvent(eventType: Int, params: Bundle?) {
        Log.d(RetrofitClient.TAG, "onEvent")
    }

    override fun onBeginningOfSpeech() {
        Log.d(RetrofitClient.TAG, "onBeginningOfSpeech")
    }

    override fun onEndOfSpeech() {
        Log.d(RetrofitClient.TAG, "onEndOfSpeech")
    }

    override fun onResults(results: Bundle?) {
        val text = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
        Log.d(RetrofitClient.TAG, text.toString())
    }

    override fun onError(error: Int) {
        Log.d(RetrofitClient.TAG, "onError")
        when(error){
            SpeechRecognizer.ERROR_AUDIO -> Log.d(RetrofitClient.TAG, "ERROR AUDIO")
            SpeechRecognizer.ERROR_CLIENT -> Log.d(RetrofitClient.TAG, "ERROR CLIENT")
            SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS -> Log.d(RetrofitClient.TAG, "ERROR_INSUFFICIENT_PERMISSIONS")
            SpeechRecognizer.ERROR_NETWORK -> Log.d(RetrofitClient.TAG, "ERROR NETWORK")
            SpeechRecognizer.ERROR_NETWORK_TIMEOUT -> Log.d(RetrofitClient.TAG, "ERROR NETWORK TIMEOUT")
            SpeechRecognizer.ERROR_NO_MATCH -> Log.d(RetrofitClient.TAG, "ERROR NO MATCH")
            SpeechRecognizer.ERROR_RECOGNIZER_BUSY -> Log.d(RetrofitClient.TAG, "ERROR RECOGNIZER BUSY")
            SpeechRecognizer.ERROR_SERVER -> Log.d(RetrofitClient.TAG, "ERROR SERVER")
            SpeechRecognizer.ERROR_SPEECH_TIMEOUT -> Log.d(RetrofitClient.TAG, "ERROR SPEECH TIMEOUT")
        }
    }
}