/*
 * Copyright 2014-2015 Red Hat, Inc, and individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.projectodd.wunderboss.web.async.websocket;

public abstract class WebsocketChannelSkeleton implements WebsocketChannel {


    public WebsocketChannelSkeleton(final OnOpen onOpen, final OnClose onClose, final OnMessage onMessage, final OnError onError) {
        this.onOpen = onOpen;
        this.onClose = onClose;
        this.onMessage = onMessage;
        this.onError = onError;
    }

    @Override
    public Object handshake() {
        return this.handshake;
    }

    @Override
    public void setHandshake(Object handshake) {
        this.handshake = handshake;
    }

    @Override
    public void notifyOpen(final Object context) {
        if (!this.openNotified &&
                this.onOpen != null) {
            this.onOpen.handle(this, context);
        }
        this.openNotified = true;
    }

    @Override
    public boolean send(Object message) throws Exception {
        return send(message, false);
    }

    protected void notifyClose(int code, String reason) {
        if (!closeNotified &&
                this.onClose != null) {
            this.onClose.handle(this, code, reason);
        }
        closeNotified = true;
    }

    protected void notifyMessage(Object message) {
        if (this.onMessage != null) {
            this.onMessage.handle(this, message);
        }
    }

    protected void notifyError(Throwable error) {
        if (this.onError != null) {
            this.onError.handle(this, error);
        }
    }

    private final OnOpen onOpen;
    private final OnClose onClose;
    private final OnMessage onMessage;
    private final OnError onError;
    private Object handshake;
    private boolean closeNotified = false;
    private boolean openNotified = false;
}
