/*
 * Copyright (C) 2015 Zhang Rui <bbcallen@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.yang.yunfan.ijkplayer.media;

import android.view.View;
import android.widget.MediaController;

public interface IMediaController {

    /**
     * 隐藏控制器
     */
    void hide();

    /**
     * 控制器是否正在显示
     */
    boolean isShowing();

    /**
     * 为控制器设置锚，即控制器依附于锚，位置随锚的位置而定
     */
    void setAnchorView(View view);

    /**
     * 打开控制器，能够正常工作
     */
    void setEnabled(boolean enabled);

    /**
     * 设置媒体播放器
     */
    void setMediaPlayer(MediaController.MediaPlayerControl player);

    /**
     * 显示控制器，指定时间后隐藏
     */
    void show(int timeout);

    /**
     * 显示控制器，默认时间后隐藏
     */
    void show();

    //----------
    // Extends
    //----------
    void showOnce(View view);
}
