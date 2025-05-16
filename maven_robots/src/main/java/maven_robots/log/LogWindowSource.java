package maven_robots.log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Что починить:
 * 1. Этот класс порождает утечку ресурсов (связанные слушатели оказываются
 * удерживаемыми в памяти)
 * 2. Этот класс хранит активные сообщения лога, но в такой реализации он 
 * их лишь накапливает. Надо же, чтобы количество сообщений в логе было ограничено 
 * величиной m_iQueueLength (т.е. реально нужна очередь сообщений 
 * ограниченного размера) 
 */
public class LogWindowSource {
    private int m_iQueueLength;
    
    private volatile ArrayBlockingQueue<LogEntry> m_messages;
    private final ArrayList<LogChangeListener> m_listeners;
    private volatile LogChangeListener[] m_activeListeners;
    
    public LogWindowSource(int iQueueLength) {
        m_iQueueLength = iQueueLength;
        m_messages = new ArrayBlockingQueue<LogEntry>(m_iQueueLength);
        m_listeners = new ArrayList<LogChangeListener>();
    }
    
    public void registerListener(LogChangeListener listener) {
        synchronized (m_listeners) {
            m_listeners.add(listener);
            m_activeListeners = null;
        }
    }
    
    public void unregisterListener(LogChangeListener listener) {
        synchronized (m_listeners) {
            m_listeners.remove(listener);
            m_activeListeners = null;
        }
    }
    
    public void append(LogLevel logLevel, String strMessage) {
        LogEntry entry = new LogEntry(logLevel, strMessage);
        synchronized (m_messages) {
            if (m_messages.size() == m_iQueueLength){
                m_messages.poll();
            }
            m_messages.add(entry);
        }
        LogChangeListener[] activeListeners = m_activeListeners;
        if (activeListeners == null) {
            synchronized (m_listeners) {
                if (m_activeListeners == null) {
                    activeListeners = m_listeners.toArray(new LogChangeListener [0]);
                    m_activeListeners = activeListeners;
                }
            }
        }
        for (LogChangeListener listener : activeListeners) {
            listener.onLogChanged();
        }
    }
    
    public int size() {
        return m_messages.size();
    }

    public int listenersCount() {
        return m_listeners.size();
    }

    public Iterable<LogEntry> range(int startFrom, int count) {
        List<LogEntry> currentMessages = Arrays.asList(m_messages.toArray(new LogEntry [0]));
        if (startFrom < 0 || startFrom >= currentMessages.size()) {
            return Collections.emptyList();
        }
        int indexTo = Math.min(startFrom + count, currentMessages.size());
        return currentMessages.subList(startFrom, indexTo - startFrom);
    }

    public Iterable<LogEntry> all() {
        return m_messages;
    }
}
