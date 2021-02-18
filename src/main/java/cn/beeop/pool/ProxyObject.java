/*
 * Copyright Chris2018998
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
package cn.beeop.pool;

import cn.beeop.BeeObjectException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static cn.beeop.pool.StaticCenter.ObjectMethodMap;

/**
 * Object base proxy
 *
 * @author Chris.Liao
 * @version 1.0
 */
public class ProxyObject {
    protected Object delegate;
    protected PooledEntry pEntry;
    protected boolean isClosed;

    public ProxyObject(PooledEntry pEntry) {
        this.pEntry = pEntry;
        pEntry.proxyObject = this;
        this.delegate = pEntry.object;
    }

    public String toString() {
        return pEntry.toString();
    }

    public boolean isClosed() throws BeeObjectException {
        return isClosed;
    }

    public final void close() throws BeeObjectException {
        synchronized (this) {//safe close
            if (isClosed) return;
            isClosed = true;
        }
        pEntry.recycleSelf();
    }

    final void trySetAsClosed() {//called from ObjectPool
        try {
            close();
        } catch (BeeObjectException e) {
        }
    }

    public final Object call(String name, Class[] types, Object[] params) throws BeeObjectException {
        try {
            if (isClosed) throw new BeeObjectException();
            MethodCallKey key = new MethodCallKey(name, types);
            Method method = ObjectMethodMap.get(key);
            if (method == null) {
                method = delegate.getClass().getMethod(name, types);
                ObjectMethodMap.put(key, method);
            }

            Object v = method.invoke(delegate, params);
            pEntry.updateAccessTime();
            return v;
        } catch (NoSuchMethodException e) {
            throw new BeeObjectException(e);
        } catch (IllegalAccessException e) {
            throw new BeeObjectException(e);
        } catch (InvocationTargetException e) {
            if (e.getCause() != null) {
                throw new BeeObjectException(e.getCause());
            } else {
                throw new BeeObjectException(e);
            }
        }
    }
}
