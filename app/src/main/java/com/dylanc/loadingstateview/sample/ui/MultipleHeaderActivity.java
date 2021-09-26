/*
 * Copyright (c) 2019. Dylan Cai
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.dylanc.loadingstateview.sample.ui;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.dylanc.loadingstateview.LoadingStateView;
import com.dylanc.loadingstateview.ViewType;
import com.dylanc.loadingstateview.sample.R;
import com.dylanc.loadingstateview.sample.delegate.NothingViewDelegate;
import com.dylanc.loadingstateview.sample.delegate.SearchHeaderViewDelegate;
import com.dylanc.loadingstateview.sample.delegate.ToolbarViewDelegate;
import com.dylanc.loadingstateview.sample.delegate.NavIconType;
import com.dylanc.loadingstateview.sample.utils.HttpUtils;

/**
 * @author Dylan Cai
 */
public class MultipleHeaderActivity extends AppCompatActivity implements SearchHeaderViewDelegate.OnSearchListener {

  private static final String VIEW_TYPE_SEARCH = "search_header";
  private LoadingStateView loadingStateView;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.layout_content);
    loadingStateView = new LoadingStateView(this);
    loadingStateView.register(ViewType.TITLE, new ToolbarViewDelegate("MultipleHeader(search)", NavIconType.BACK));
    loadingStateView.register(VIEW_TYPE_SEARCH, new SearchHeaderViewDelegate(this));
    loadingStateView.register(ViewType.EMPTY, new NothingViewDelegate());
    loadingStateView.setDecorHeader(ViewType.TITLE, VIEW_TYPE_SEARCH);
    loadingStateView.showEmptyView();
  }

  @Override
  public void onSearch(String keyword) {
    Toast.makeText(this, "search: " + keyword, Toast.LENGTH_SHORT).show();
    loadingStateView.showLoadingView();
    HttpUtils.requestSuccess(new HttpUtils.Callback() {
      @Override
      public void onSuccess() {
        loadingStateView.showContentView();
      }

      @Override
      public void onFailure() {
        loadingStateView.showErrorView();
      }
    });
  }
}