package in.abmulani.crossoverassignment;

import android.app.Fragment;

/**
 * Created by aabid-personal on 3/14/16.
 */
public abstract class BaseFragment extends Fragment {

    // to manage back key event from the current fragment
    public abstract boolean onBackKeyConsumed();

}
