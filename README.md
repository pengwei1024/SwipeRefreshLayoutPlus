# SwipeRefreshLayoutPlus
a powerful SwipeRefreshLayout widget for android

## <font color='red'>目前尚存在bug，请勿使用在项目中  </font>

### Gradle
```java
compile 'com.apkfuns.swiperefreshlayoutplus:swiperefreshlayoutplus:1.0'
```
### Usage
```xml
<com.apkfuns.swiperefreshlayoutplus.SwipeRefreshLayoutPlus xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:id="@+id/refreshView"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    app:mLoadMoreView="@layout/view_normal_refresh_footer"
    app:mRefreshView="@layout/view_normal_refresh_header"
    app:mRefreshViewBackgroundColor="#ddd"
    app:mScrollableChildId="@+id/listView">

    <ListView
        android:id="@+id/listView"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />
</com.apkfuns.swiperefreshlayoutplus.SwipeRefreshLayoutPlus>
```
### Attributes
属性 | 值 |含义
------- | -------| -------
mLoadMoreView | reference | 上拉加载更多视图布局id
mRefreshView | reference | 下拉刷新视图布局id
mRefreshViewBackgroundColor | color |下拉刷新视图背景颜色
mScrollableChildId | reference | 包含的ScrollView,ListView,RecyclerView等Id值

### Thanks
[SuperSwipeRefreshLayout](https://github.com/nuptboyzhb/SuperSwipeRefreshLayout)

### License
<pre>
Copyright 2015 Orhan Obut

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
</pre>

