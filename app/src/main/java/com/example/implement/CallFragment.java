package com.example.implement;

import static com.example.implement.MainActivity.TAG;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mifonelibproj.core.Factory;

import java.util.Timer;
import java.util.TimerTask;

public class CallFragment extends Fragment {

    String mNumberPhone;
    int mType;
    public static int TYPE_INCOMING = 0;
    public static int TYPE_CALL_OUT = 1;
    public static int TYPE_CONNECTED = 2;
    public CallFragment(String numberPhone,int type) {
        // Required empty public constructor
        mNumberPhone = numberPhone;
        mType = type;
    }

    View view;
    Timer timer;
    int countTime = 0;
    TextView tvDuration,tvNumbPhone;
    LinearLayout grOpt;
    ImageView btnEnd,btnAccept,optTransfer,optToggleSpeaker,optToggleMic;
    TextView optToggle;

    private String convertTimeToString(int countTime){
        int second = countTime % 60;
        int minute = countTime / 60;
        return ((minute<10)?("0"+minute):String.valueOf(minute)) +":"+ ((second<10)?("0"+second):String.valueOf(second));
    }
    boolean isPaused = false;
    boolean isMuteMic = false;
    boolean isOnSpeaker = false;
    Drawable pause,resume,muteMic,onMic,offSpeaker,onSpeaker,hold,holdSelected;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_call, container, false);
        pause = getResources().getDrawable(R.drawable.pause);
        resume = getResources().getDrawable(R.drawable.resume);
        muteMic = getResources().getDrawable(R.drawable.mute_mic);
        onMic = getResources().getDrawable(R.drawable.on_mic);
        hold = getResources().getDrawable(R.drawable.shape_hold);
        holdSelected = getResources().getDrawable(R.drawable.shape_hold_selected);
        offSpeaker = getResources().getDrawable(R.drawable.speaker_off);
        onSpeaker = getResources().getDrawable(R.drawable.speaker_on);
        timer = new Timer();
        tvNumbPhone = view.findViewById(R.id.tv_numb_phone);
        tvDuration = view.findViewById(R.id.timing);
        btnAccept = view.findViewById(R.id.btn_answer);
        optToggleSpeaker = view.findViewById(R.id.opt_toggle_speaker);
        optToggleMic = view.findViewById(R.id.opt_toggle_mic);
        btnEnd = view.findViewById(R.id.btn_end_call);
        grOpt = view.findViewById(R.id.gr_opt);
        optToggle = view.findViewById(R.id.opt_toggle_pause_or_resume);
        optTransfer = view.findViewById(R.id.opt_transfer);
        optToggle.setOnClickListener(v->{
            isPaused = !isPaused;
            optToggle.setBackground((isPaused)?holdSelected:hold);
            optToggle.setTextColor(Color.parseColor((isPaused)?"#FF000000":"#FFFFFFFF"));
            if(isPaused) Factory.holdCall();
            else Factory.resumCall();
        });
        optToggleMic.setOnClickListener(v->{
            isMuteMic = !isMuteMic;
            optToggleMic.setImageDrawable((isMuteMic)?muteMic:onMic);
            AudioManager audioManager = (AudioManager) requireActivity().getSystemService(Context.AUDIO_SERVICE);
            audioManager.setMode(AudioManager.MODE_IN_CALL);
            audioManager.setMicrophoneMute(isMuteMic);
        });
        optToggleSpeaker.setOnClickListener(v->{
            isOnSpeaker = !isOnSpeaker;
            optToggleSpeaker.setImageDrawable((isOnSpeaker)?onSpeaker:offSpeaker);
            AudioManager audioManager = (AudioManager) requireActivity().getSystemService(Context.AUDIO_SERVICE);
            audioManager.setMode(AudioManager.MODE_IN_CALL);
            audioManager.setSpeakerphoneOn(isOnSpeaker);
        });
        optTransfer.setOnClickListener(v->{
            FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
            transaction.addToBackStack(null);
            transaction.add(R.id.frame_fragment,new TransferFragment(),"TRANSFER_FRAGMENT");
            transaction.commit();
        });
        Log.d(TAG, "onCreateView: "+mType);
        if(mNumberPhone==null){
            tvNumbPhone.setText(Factory.getNumbPhoneCallIn());
        }
        if (mType == TYPE_CONNECTED) {
            grOpt.setVisibility(View.VISIBLE);
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    new Handler(Looper.getMainLooper()).post(()-> {
                        tvDuration.setText(convertTimeToString(++countTime));
                    });
                }
            },0,1000);
            btnAccept.setVisibility(View.GONE);
        } else if (mType == TYPE_CALL_OUT){
            Factory.makeCall(mNumberPhone);
            tvNumbPhone.setText(mNumberPhone);
            btnAccept.setVisibility(View.GONE);
        }
        btnAccept.setOnClickListener(v->{
            Factory.acceptCall();
            btnAccept.setVisibility(View.GONE);
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    new Handler(Looper.getMainLooper()).post(
                            () -> tvDuration.setText(convertTimeToString(++countTime))
                    );
                }
            },0,1000);
        });
        btnEnd.setOnClickListener(v->{
            Factory.cancelCall();
        });
        return view;
    }
}