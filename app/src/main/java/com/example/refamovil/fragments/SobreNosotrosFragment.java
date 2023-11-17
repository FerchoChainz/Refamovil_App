package com.example.refamovil.fragments;

import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.VideoView;

import com.example.refamovil.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SobreNosotrosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SobreNosotrosFragment extends Fragment {
    private VideoView videoView;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SobreNosotrosFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SobreNosotrosFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SobreNosotrosFragment newInstance(String param1, String param2) {
        SobreNosotrosFragment fragment = new SobreNosotrosFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sobre_nosotros, container, false);

        videoView = view.findViewById(R.id.videoView);

        // Especifica la ruta del video
        String videoPath = "android.resource://" + getActivity().getPackageName() + "/" + R.raw.video1;

        // Configura el Uri para el video
        Uri uri = Uri.parse(videoPath);

        // Configura un MediaController para el VideoView
        MediaController mediaController = new MediaController(getActivity());
        mediaController.setAnchorView(videoView);

        // Asigna el Uri al VideoView y establece el MediaController
        videoView.setVideoURI(uri);
        videoView.setMediaController(mediaController);

        return view;
    }
}