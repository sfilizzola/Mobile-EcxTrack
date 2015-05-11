package dev.ecxtrack.ecxtrack;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by samuel.filizzola on 05/05/2015.
 */
public class ScreenSlidePageFragment extends Fragment {

    private String PName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_screen_slide, container, false);

        ImageView img = (ImageView)rootView.findViewById(R.id.imgTuto);

        String uri = "@drawable/"+PName;
        int imageResource = getResources().getIdentifier(uri, null, getActivity().getPackageName());
        Drawable res = getResources().getDrawable(imageResource);
        img.setImageDrawable(res);

        return rootView;
    }

    public String getPName() {
        return PName;
    }

    public void setPName(String PName) {
        this.PName = PName;
    }
}
