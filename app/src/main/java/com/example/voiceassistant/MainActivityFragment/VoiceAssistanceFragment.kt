package com.example.voiceassistant.MainActivityFragment

import android.Manifest
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.preference.PreferenceManager
import android.speech.RecognitionListener
import android.speech.SpeechRecognizer
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import com.example.voiceassistant.Adapter.ChatAdapter
import com.example.voiceassistant.Database.WeatherRepository
import com.example.voiceassistant.Enums.Sender
import com.example.voiceassistant.MainActivity
import com.example.voiceassistant.Model.GoogleSpeaker
import com.example.voiceassistant.Model.Message
import com.example.voiceassistant.Model.Weather.Weather
import com.example.voiceassistant.R
import com.example.voiceassistant.Retrofit.RetrofitClient
import com.example.voiceassistant.Util.TimeUtils
import com.example.voiceassistant.Util.VoiceController
import com.example.voiceassistant.Util.VoiceRecognition
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class VoiceAssistanceFragment : Fragment(), RecognitionListener{

    private lateinit var viewManager : RecyclerView.LayoutManager
    private lateinit var googleSpeaker: GoogleSpeaker
    private lateinit var voiceController: VoiceController
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    lateinit var prefs: SharedPreferences

    companion object {
        val messages = mutableListOf<Message>()
        lateinit var messagesList: RecyclerView
        lateinit var viewAdapter : RecyclerView.Adapter<*>
        lateinit var lastQueryBtn: Button

        lateinit var voiceContext: Context

        fun newInstance(): VoiceAssistanceFragment = VoiceAssistanceFragment()
        fun addMessage(message : Message){
            messages.add(message)
            viewAdapter.notifyDataSetChanged()
            messagesList.scrollToPosition(viewAdapter.itemCount - 1)
        }

        fun updateLastQuery(text : String){
            lastQueryBtn.text = text
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.voice_assistance_fragment, container, false)
        prefs = PreferenceManager.getDefaultSharedPreferences(context)
        val lastLocation = prefs.getString("last_location", "Barcelona")
        val hasWelcomed = prefs.getBoolean("welcomeMessage", false)

        lastQueryBtn = view.findViewById(R.id.buttonQuery)
        updateLastQuery(prefs.getString("lastQuery", "None"))

        lastQueryBtn.setOnClickListener {
            processVoiceInput(lastQueryBtn.text.toString())
        }

        val voiceButton = view.findViewById<ImageButton>(R.id.voiceButton)
        voiceContext = activity?.applicationContext!!
        voiceController = VoiceController(voiceContext)

        viewManager = LinearLayoutManager(activity)
        viewAdapter = ChatAdapter(messages)
        googleSpeaker = GoogleSpeaker(activity?.applicationContext!!)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity?.applicationContext!!)

        messagesList = view.findViewById<RecyclerView>(R.id.chatList).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }

        val speechRecognizer = SpeechRecognizer.createSpeechRecognizer(activity)
        speechRecognizer.setRecognitionListener(this)

        val recognizerIntent = VoiceRecognition().createSpeechRecognizer()

        voiceButton.setOnClickListener {
            speechRecognizer.startListening(recognizerIntent)
        }

        val locationPermission = ContextCompat.checkSelfPermission(
            activity?.applicationContext!!,
            Manifest.permission.ACCESS_FINE_LOCATION)

        if(locationPermission == PackageManager.PERMISSION_GRANTED){
            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                val latitude = location?.latitude
                val longitude = location?.longitude
                val geocoder = Geocoder(activity?.applicationContext!!, Locale.getDefault())

                val address = geocoder.getFromLocation(latitude!!, longitude!!, 1)
                if(address.isNotEmpty()){
                    val prefs = PreferenceManager.getDefaultSharedPreferences(activity?.applicationContext!!)
                    val editor = prefs.edit()
                    editor.putString("last_location", address[0].locality)
                    editor.apply()
                }
            }
        }

        if(!hasWelcomed){
            RetrofitClient.getWeatherByName(lastLocation, "TELL ME THE WEATHER", property = "welcomeMessage", voiceActivated = false)
            val editor = prefs.edit()
            editor.putBoolean("welcomeMessage", true)
            editor.apply()
        }

        return view
    }

    override fun onResults(results: Bundle?) {
        val voiceInput = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)?.get(0).toString().capitalize()
        processVoiceInput(voiceInput)
    }

    override fun onError(error: Int) {
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


    override fun onReadyForSpeech(params: Bundle?){}
    override fun onRmsChanged(rmsdB: Float){}
    override fun onBufferReceived(buffer: ByteArray?){}
    override fun onPartialResults(partialResults: Bundle?){}
    override fun onEvent(eventType: Int, params: Bundle?){}
    override fun onBeginningOfSpeech(){}
    override fun onEndOfSpeech(){}
    override fun onDestroy(){
        super.onDestroy()
        googleSpeaker.destroy()
    }

    private fun processVoiceInput(input: String){
        if(input.toUpperCase().equals("SHOW ME MY TO DO LIST")){
            updateLastQuery(input)
            addMessage(Message(messages.size, Sender.USER, input, TimeUtils.getCurrentTime()))
            val editor = prefs.edit().putString("lastQuery", input)
            editor.apply()

            activity?.supportFragmentManager?.beginTransaction()
                ?.hide(MainActivity.activeFragment)?.show(MainActivity.todoListFragment)?.commit()

            MainActivity.activeFragment = MainActivity.todoListFragment
            MainActivity.toolbar.title = getString(R.string.todolist_title)
            MainActivity.navigationBar.menu.getItem(2).setChecked(true)
        }else{
            addMessage(Message(messages.size, Sender.USER, input, TimeUtils.getCurrentTime()))
            voiceController.processVoiceInput(input)
        }
    }
}