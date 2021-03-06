package com.htoja.mifik.htoja.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.htoja.mifik.htoja.R;
import com.htoja.mifik.htoja.control.TeamGameManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mi on 3/2/2017.
 */
public class NextTeamFragment extends Fragment {
    private ListView listView;
    private NextTeamFragment.ListAdapter adapter;
    private TextView team;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle bundle) {
        return inflater.inflate(R.layout.fragment_next_team, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView target = (TextView) getActivity().findViewById(R.id.tvTargetPoints);
        target.setText(" " + String.valueOf(TeamGameManager.getInstance().getPointsToWin()));

        TextView round = (TextView) getActivity().findViewById(R.id.tvRoundCount);
        round.setText(" " + String.valueOf(TeamGameManager.getInstance().getRound()));

        listView = (ListView) getActivity().findViewById(R.id.list);
        adapter = new NextTeamFragment.ListAdapter(getContext(), R.layout.team_result_item, new ArrayList<String>());
        listView.setAdapter(adapter);
        team = (TextView) view.findViewById(R.id.tvNextName);

        adapter.addAll(TeamGameManager.getInstance().getTeamResults().keySet());

        if (TeamGameManager.getInstance().hasEnded()) {
            showEndedMessage();
        } else {
            team.setText(TeamGameManager.getInstance().getCurrentTeam());
        }
    }

    private void showEndedMessage() {
        team.setText(TeamGameManager.getInstance().getVictorian());

        TextView header = (TextView) getView().findViewById(R.id.tvNextHeader);
        header.setText(getActivity().getString(R.string.winner));

        Button btn = (Button) getActivity().findViewById(R.id.btPlay);
        btn.setText(getActivity().getText(R.string.title_menu));
    }

    private class ListAdapter extends ArrayAdapter<String> {
        private final List<String> data;
        private final Context context;

        ListAdapter(Context context, int resource, List<String> data) {
            super(context, resource, data);
            this.data = data;
            this.context = context;
        }

        public List<String> getData() {
            return data;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final NextTeamFragment.ListAdapter.ViewHolder viewHolder;
            if (convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(context);
                convertView = inflater.inflate(R.layout.team_result_item, null);
                viewHolder = new NextTeamFragment.ListAdapter.ViewHolder();
                viewHolder.text = (TextView) convertView
                        .findViewById(R.id.tvTeamName);
                viewHolder.result = (TextView) convertView
                        .findViewById(R.id.tvResult);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (NextTeamFragment.ListAdapter.ViewHolder) convertView.getTag();
            }

            final String team = getItem(position);
            viewHolder.text.setText(team);

            Integer result = TeamGameManager.getInstance().getTeamResults().get(team);
            viewHolder.result.setText(result.toString());

            return convertView;
        }

        class ViewHolder {
            TextView text;
            TextView result;
        }
    }
}