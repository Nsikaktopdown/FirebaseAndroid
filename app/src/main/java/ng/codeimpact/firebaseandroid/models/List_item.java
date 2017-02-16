package ng.codeimpact.firebaseandroid.models;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by Nsikak  Thompson on 2/15/2017.
 */

public class List_item {

       /* public String uid;
        public String author;*/
        public String title;
        public String body;


        public List_item() {
            // Default constructor required for calls to DataSnapshot.getValue(Post.class)
        }

        public List_item(String uid, String author, String title, String body) {
            /*this.uid = uid;
            this.author = author;*/
            this.title = title;
            this.body = body;
        }

        // [START post_to_map]
        @Exclude
        public Map<String, Object> toMap() {
            HashMap<String, Object> result = new HashMap<>();
           /* result.put("uid", uid);
            result.put("author", author);*/
            result.put("title", title);
            result.put("body", body);


            return result;
        }
        // [END post_to_map]

    }
// [END post_class]


