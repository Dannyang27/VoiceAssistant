package com.example.voiceassistant.MainActivityFragment

import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.SpeechRecognizer
import android.speech.tts.TextToSpeech
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import com.example.voiceassistant.Adapter.ChatAdapter
import com.example.voiceassistant.Enums.Sender
import com.example.voiceassistant.Model.GoogleSpeaker
import com.example.voiceassistant.Model.Message
import com.example.voiceassistant.R
import com.example.voiceassistant.Retrofit.RetrofitClient
import com.example.voiceassistant.Util.VoiceController
import com.example.voiceassistant.Util.VoiceRecognition
import java.util.*

class VoiceAssistanceFragment : Fragment(), RecognitionListener{

    private lateinit var viewManager : RecyclerView.LayoutManager
    private lateinit var googleSpeaker: GoogleSpeaker


    companion object {
        val messages = mutableListOf<Message>()
        lateinit var messagesList: RecyclerView
        lateinit var viewAdapter : RecyclerView.Adapter<*>

        fun newInstance(): VoiceAssistanceFragment = VoiceAssistanceFragment()
        fun addMessage(message : Message){
            RetrofitClient.getWeatherByNameSync("London")
            messages.add(message)
            viewAdapter.notifyDataSetChanged()
            messagesList.scrollToPosition(viewAdapter.itemCount - 1)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.voice_assistance_fragment, container, false)

        val voiceButton = view.findViewById<ImageButton>(R.id.voiceButton)

        viewManager = LinearLayoutManager(activity)
        viewAdapter = ChatAdapter(messages)
        googleSpeaker = GoogleSpeaker(activity?.applicationContext!!)


        messagesList = view.findViewById<RecyclerView>(R.id.chatList).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }

        val speechRecognizer = SpeechRecognizer.createSpeechRecognizer(activity)
        speechRecognizer.setRecognitionListener(this)

        val recognizerIntent = VoiceRecognition().createSpeechRecognizer()

        voiceButton.setOnClickListener {
            Log.d(RetrofitClient.TAG, messages.toString())
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
        Log.d(RetrofitClient.TAG, "onResults")
        val voiceInput = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)?.get(0).toString().capitalize()
        val message = Message(messages.size, Sender.USER, voiceInput)

        addMessage(message)
        val voiceText = VoiceController.processVoiceInput(voiceInput)

        googleSpeaker.speak(voiceText)
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

    override fun onDestroy() {
        super.onDestroy()
        googleSpeaker.destroy()
    }
}