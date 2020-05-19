package com.example.oralpractise;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Myadapter extends BaseAdapter {


    public ArrayList<File>  data = new ArrayList<File>();
    public Context context;

    public Myadapter(Context context)
    {
        this.context=context;
    }
    public void addmedia( File file){
        data.add(file);
        notifyDataSetChanged();


    }
    public File addmediareturnfile(File file)

    {
        data.add(file);
        notifyDataSetChanged();
        return file;

    }
    public void remove(int e){
        data.remove(e);
        notifyDataSetChanged();
    }
    public void remove(File e ){
        data.remove(e);
        notifyDataSetChanged();

    }


    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        final int position1 = position;
        LinearLayout itemLayout = (LinearLayout) LayoutInflater.from(
                parent.getContext()).inflate(R.layout.playdata,null);
        final Button play=((Button)itemLayout.findViewById(R.id.playbutton));
        play.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                MediaPlayer player;player = new MediaPlayer();
                if (play.getText()!="stopplay"){

                    try {
                        player.setDataSource(data.get(position1).getPath());
                        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            @SuppressLint("SetTextI18n")
                            @Override
                            public void onCompletion(MediaPlayer mp) {
                                play.setText("play");
                            }
                        });
                        player.prepare();

                        player.start();
                    } catch (IOException e) {
                        Log.e("LOG_TAG", "prepare() failed");

                    }play.setText("stopplay");}else
                {
                    player.stop();
                    play.setText("play");
                }
            }});

        final TextView textView= itemLayout.findViewById(R.id.textViewname);
        textView.setText(data.get(position1).toString().substring(data.get(position1).toString().length()-12));

        final Button delete = itemLayout.findViewById(R.id.deletebutton);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File fdelete = data.get(position1);
                if (fdelete.exists()) {
                    if (fdelete.delete()) {
                        Toast.makeText( parent.getContext(),"file Deleted :" + fdelete.getPath(),Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText( parent.getContext(),"file Not Deleted :" + fdelete.getPath(),Toast.LENGTH_SHORT).show();
                    }
                }
               remove(position1);
            }
        });
        return itemLayout;
    }
}
