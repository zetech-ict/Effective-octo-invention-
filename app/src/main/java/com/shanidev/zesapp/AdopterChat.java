package com.shanidev.zesapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdopterChat extends AppCompatActivity {
        private static final int MSG_TYPE_LEFT = 0;
        private static final int MSG_TYPR_RIGHT = 1;
        Context context;
        List<Chat> list;
        String imageurl;
        FirebaseUser firebaseUser;

    public AdopterChat(Context context, List<Chat> list, String imageurl) {
            this.context = context;
            this.list = list;
            this.imageurl = imageurl;
        }

        @NonNull
        public Myholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            if (viewType == MSG_TYPE_LEFT) {
                View view = LayoutInflater.from(context).inflate(R.layout.activity_rawchart_left, parent, false);
                return new Myholder(view);
            } else {
                View view = LayoutInflater.from(context).inflate(R.layout.activity_rowchat_right, parent, false);
                return new Myholder(view);
            }
        }

        public void onBindViewHolder(@NonNull Myholder holder, final int position) {
            String message = list.get(position).getMessage();
            String timeStamp = list.get(position).getTimestamp();
            String type = list.get(position).getType();
            Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
            calendar.setTimeInMillis(Long.parseLong(timeStamp));
            String timedate = DateFormat.format("dd/MM/yyyy hh:mm aa", calendar).toString();
            holder.message.setText(message);
            holder.time.setText(timedate);
            try {

            } catch (Exception e) {

            }
            if (type.equals("text")) {
                holder.message.setVisibility(View.VISIBLE);
                holder.mimage.setVisibility(View.GONE);
                holder.message.setText(message);
            } else {
                holder.message.setVisibility(View.GONE);
                holder.mimage.setVisibility(View.VISIBLE);

            }

            holder.msglayput.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Delete Message");
                    builder.setMessage("Are You Sure To Delete This Message");
                    builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            deleteMsg(position);
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.create().show();
                }
            });
        }

        private void deleteMsg(int position) {
            final String myuid = FirebaseAuth.getInstance().getCurrentUser().getUid();
            String msgtimestmp = list.get(position).getTimestamp();
            DatabaseReference dbref = FirebaseDatabase.getInstance().getReference().child("Chats");
            Query query = dbref.orderByChild("timestamp").equalTo(msgtimestmp);
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        if (dataSnapshot1.child("sender").getValue().equals(myuid)) {
                            // any two of below can be used
                            dataSnapshot1.getRef().removeValue();
                       /* HashMap<String, Object> hashMap = new HashMap<>();
                        hashMap.put("message", "This Message Was Deleted");
                        dataSnapshot1.getRef().updateChildren(hashMap);
                        Toast.makeText(context,"Message Deleted.....",Toast.LENGTH_LONG).show();
*/
                        } else {
                            Toast.makeText(context, "you can delet only your msg....", Toast.LENGTH_LONG).show();
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

        public int getItemCount() {
            return list.size();
        }
        public int getItemViewType(int position) {
            firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
            if (list.get(position).getSender().equals(firebaseUser.getUid())) {
                return MSG_TYPR_RIGHT;
            } else {
                return MSG_TYPE_LEFT;
            }
        }

    public void notifyDataSetChanged() {
    }

    class Myholder extends RecyclerView.ViewHolder {

            CircleImageView image;
            ImageView mimage;
            TextView message, time, isSee;
            LinearLayout msglayput;

            public Myholder(@NonNull View itemView) {
                super(itemView);
                image = itemView.findViewById(R.id.profilec);
                message = itemView.findViewById(R.id.msgc);
                time = itemView.findViewById(R.id.timetv);
                isSee = itemView.findViewById(R.id.isSeen);
                msglayput = itemView.findViewById(R.id.msglayout);
                mimage = itemView.findViewById(R.id.images);
            }
        }
    }