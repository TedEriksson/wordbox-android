package uk.co.vism.wordbox.fragments;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.util.Log;

import io.realm.Realm;
import uk.co.vism.wordbox.managers.UserManager;
import uk.co.vism.wordbox.models.User;

public abstract class WordBoxFragment extends Fragment {
    OnUserLoaded activity;
    Realm realm;
    User user;

    public interface OnUserLoaded {
        public void onUserLoaded(User user);
    }

    public abstract void updateData();

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        realm = Realm.getInstance(getActivity());

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            this.activity = (OnUserLoaded) activity;
        }
        catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnUserLoaded");
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        realm.close();
    }
}