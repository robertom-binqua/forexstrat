package com.binqua.forexstrat.feedreader.core.actions;

import com.binqua.forexstrat.feedreader.core.client.FeedReadResponse;

public interface FeedReaderResponseAction {

    void actOn(FeedReadResponse feedReadResponse);

}
