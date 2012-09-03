package org.devnull.client.spring.cache

import java.util.concurrent.locks.Lock
import java.util.concurrent.locks.ReentrantLock

class PropertiesObjectFileSystemStore implements PropertiesObjectStore {
    File parent
    Lock lock = new ReentrantLock()

    PropertiesObjectFileSystemStore(File parent) {
        this.parent = parent
    }

    void put(String environment, String name, Properties props) {
        doWhileLocked {
            def file = new File(parent, "${environment}-${name}.properties")
            def writer = new FileWriter(file)
            try {
                props.store(writer, "cached copy")
            } finally {
                writer.close()
            }
        }
    }

    Properties get(String environment, String name) {
        doWhileLocked {
            def props = new Properties()
            def file = new File(parent, "${environment}-${name}.properties")
            def stream = new FileInputStream(file)
            try {
                props.load(stream)
            } finally {
                stream.close()
            }
            return props
        }
    }

    protected def doWhileLocked = { closure ->
        lock.lock()
        try {
            closure()
        }
        finally {
            lock.unlock()
        }
    }
}