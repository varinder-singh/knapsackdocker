package com.example.generator;

import com.example.utils.RandStringUtils;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

public class StringSequenceGenerator implements IdentifierGenerator {

    private static Logger log = Logger.getLogger(String.valueOf(StringSequenceGenerator.class));
    @Override
    public Serializable generate(SharedSessionContractImplementor sharedSessionContractImplementor, Object o) throws HibernateException {

        Connection connection = sharedSessionContractImplementor.connection();

        try{
            PreparedStatement ps =connection.prepareStatement("Select nextval('knapsackSeq') as nextval");
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                int id = rs.getInt("nextval");
                return RandStringUtils.generateRandomString();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
}
