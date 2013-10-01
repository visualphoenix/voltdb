/* This file is part of VoltDB.
 * Copyright (C) 2008-2013 VoltDB Inc.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with VoltDB.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.voltdb.messaging;

import org.voltcore.messaging.Subject;
import org.voltcore.messaging.VoltMessage;

import java.io.IOException;
import java.nio.ByteBuffer;

public class BalancePartitionsReadyMessage extends VoltMessage {
    private long m_readyHSId = -1;
    private long m_txnId = -1;

    /** Empty constructor for deserialization */
    public BalancePartitionsReadyMessage()
    {
        m_subject = Subject.DEFAULT.getId();
    }

    public BalancePartitionsReadyMessage(long hsId, long txnId)
    {
        this();
        m_readyHSId = hsId;
        m_txnId = txnId;
    }

    public long getReadyHSId()
    {
        return m_readyHSId;
    }

    public long getTxnId()
    {
        return m_txnId;
    }

    @Override
    public int getSerializedSize()
    {
        int msgSize = super.getSerializedSize() +
                      8 + // readyHSId
                      8;  // txnId
        return msgSize;
    }

    @Override
    protected void initFromBuffer(ByteBuffer buf) throws IOException
    {
        m_readyHSId = buf.getLong();
        m_txnId = buf.getLong();
    }

    @Override
    public void flattenToBuffer(ByteBuffer buf) throws IOException
    {
        buf.put(VoltDbMessageFactory.BALANCE_PARTITIONS_READY_ID);
        buf.putLong(m_readyHSId);
        buf.putLong(m_txnId);
    }
}
