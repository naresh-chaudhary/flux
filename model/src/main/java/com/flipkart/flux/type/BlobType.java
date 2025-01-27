/*
 * Copyright 2012-2016, the original author or authors.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.flipkart.flux.type;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.usertype.UserType;

import java.io.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

/**
 * <code>BlobType</code> is a Hibernate {@link UserType} implementation to store object as byte array in DB
 *
 * @author shyam.akirala
 */
public class BlobType implements UserType, Serializable {
    @Override
    public int[] sqlTypes() {
        return new int[]{Types.JAVA_OBJECT};
    }

    @Override
    public Class<?> returnedClass() {
        return Object.class;
    }

    @Override
    public boolean equals(Object x, Object y) throws HibernateException {
        if (x == null) {
            return (y == null);
        }

        return x.equals(y);
    }

    @Override
    public int hashCode(Object x) throws HibernateException {
        return x.hashCode();
    }

    @Override
    public void nullSafeSet(PreparedStatement st, Object value, int index, SessionImplementor session) throws HibernateException, SQLException {
        try {
            st.setBytes(index, serialize(value));
        } catch (IOException e) {
            throw new SQLException("Cannot serialize object to byte array. Exception " + e.getMessage());
        }
    }

    @Override
    public Object nullSafeGet(ResultSet rs, String[] names, SessionImplementor session, Object owner) throws HibernateException, SQLException {
        byte[] value = rs.getBytes(names[0]);

        if (value == null) {
            return null;
        }

        try {
            return deSerialize(value);
        } catch (IOException | ClassNotFoundException e) {
            throw new SQLException("Cannot deserialize byte array. Exception " + e.getMessage());
        }
    }

    /**
     * Provides deep copy of an object using serialization and de-serialization
     */
    @Override
    public Object deepCopy(Object value) throws HibernateException {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(value);
            byte[] bytes = baos.toByteArray();
            ByteArrayInputStream baip = new ByteArrayInputStream(bytes);
            ObjectInputStream ois = new ObjectInputStream(baip);
            return ois.readObject();
        } catch (Exception e) {
            throw new HibernateException("Unable to deep copy. Exception: " + e.getMessage());
        }
    }

    @Override
    public boolean isMutable() {
        return false;
    }

    @Override
    public Serializable disassemble(Object value) throws HibernateException {
        return (Serializable) this.deepCopy(value);
    }

    @Override
    public Object assemble(Serializable cached, Object owner) throws HibernateException {
        return this.deepCopy(cached);
    }

    @Override
    public Object replace(Object original, Object target, Object owner) throws HibernateException {
        return original;
    }

    /**
     * converts fetched byte array to Object
     *
     * @param value of type byte array
     * @return object
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public Object deSerialize(byte[] value) throws IOException, ClassNotFoundException {
        ByteArrayInputStream baip = new ByteArrayInputStream(value);
        ObjectInputStream ois = new ObjectInputStream(baip);
        return ois.readObject();
    }

    /**
     * converts Object to byte array to store in DB
     *
     * @param value of type Object
     * @return byte array
     * @throws IOException
     */
    public byte[] serialize(Object value) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(value);
        return baos.toByteArray();
    }
}