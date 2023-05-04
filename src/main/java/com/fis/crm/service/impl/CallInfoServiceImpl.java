package com.fis.crm.service.impl;

import com.fis.crm.domain.CallInfo;
import com.fis.crm.repository.CallInfoRepository;
import com.fis.crm.service.CallInfoService;
import com.fis.crm.service.dto.CallInfoDTO;
import com.fis.crm.service.mapper.CallInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.EntityManagerFactoryInfo;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Service
public class CallInfoServiceImpl implements CallInfoService {

    @Autowired
    private  DataSource dataSource;

    private final CallInfoRepository callInfoRepository;

    private final CallInfoMapper callInfoMapper;

    public CallInfoServiceImpl(CallInfoRepository callInfoRepository, CallInfoMapper callInfoMapper) {
        this.callInfoRepository = callInfoRepository;
        this.callInfoMapper = callInfoMapper;
    }

    @Override
    public CallInfoDTO create(CallInfoDTO callInfoDTO) {
        try {
            CallInfo callInfo = callInfoMapper.toEntity(callInfoDTO);
            callInfo.setDuration(convertToSecond(callInfo.getTalkingTime()));
            callInfo = callInfoRepository.save(callInfo);
//            updateCallDetail(callInfo);
            return callInfoMapper.toDto(callInfo);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * Update call detail
     */
    public void updateCallDetail(CallInfo callInfo)
    {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs=null;
        long ticketId=0;
        try
        {
            System.out.println("Ban ghi moi :"+callInfo.getNumberPhone());
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);
            //Cuoc goi vao
            if(callInfo.getDirection().toLowerCase().equals("in")) {
                String strSQL = "select t.* from ticket t,customer c where t.customer_id=c.customer_id "+
                    " and t.create_datetime>=sysdate-1 and c.phone_number=? and " +
                    " t.start_call is null order by t.create_datetime desc";
                pstmt=connection.prepareStatement(strSQL);
                pstmt.setString(1,callInfo.getNumberPhone());
                rs=pstmt.executeQuery();
                if(rs.next()) {
                    ticketId = rs.getLong("ticket_id");
                    System.out.println("Cuoc goi vao ticketId="+ticketId+", Phone="+callInfo.getNumberPhone());
                    closeCnn(rs);
                    closeCnn(pstmt);
                    strSQL = "update ticket set duration=?,start_call=?,call_file=?,duration_number=? where ticket_id=?";
                    pstmt = connection.prepareStatement(strSQL);
                    pstmt.setString(1, callInfo.getTalkingTime());
                    pstmt.setString((int) 2, "");
                    pstmt.setString((int) 3, callInfo.getRecording());
                    pstmt.setInt((int) 4, convertToSecond(callInfo.getTalkingTime()));
                    pstmt.setLong((int) 5, ticketId);
                    pstmt.executeUpdate();
                }
            }
            else
            {
                String strSQL = "select * from campaign_resource_detail_his where create_datetime>=sysdate-1 and phone_number=? and " +
                    " start_call is null order by create_datetime desc";
                pstmt=connection.prepareStatement(strSQL);
                pstmt.setString(1,callInfo.getNumberPhone());
                rs=pstmt.executeQuery();
                long campaignResourceId=0;
                if(rs.next()) {
                    ticketId = rs.getLong("id");
                    campaignResourceId = rs.getLong("campaign_resource_detail_id");
                    System.out.println("Cuoc goi ra ticketId="+ticketId+", Phone="+callInfo.getNumberPhone());
                    closeCnn(rs);
                    closeCnn(pstmt);
                    strSQL = "update campaign_resource_detail set duration=?,start_call=?,call_file=?,duration_number=? where id=?";
                    pstmt = connection.prepareStatement(strSQL);
                    pstmt.setString(1, callInfo.getTalkingTime());
                    pstmt.setString((int) 2, "");
                    pstmt.setString((int) 3, callInfo.getRecording());
                    pstmt.setInt((int) 4, convertToSecond(callInfo.getTalkingTime()));
                    pstmt.setLong((int) 5, campaignResourceId);
                    pstmt.executeUpdate();
                    ///////
                    closeCnn(pstmt);
                    strSQL = "update campaign_resource_detail_his set duration=?,start_call=?,call_file=?,duration_number=? where id=?";
                    pstmt = connection.prepareStatement(strSQL);
                    pstmt.setString(1, callInfo.getTalkingTime());
                    pstmt.setString((int) 2, "");
                    pstmt.setString((int) 3, callInfo.getRecording());
                    pstmt.setInt((int) 4, convertToSecond(callInfo.getTalkingTime()));
                    pstmt.setLong((int) 5, ticketId);
                    pstmt.executeUpdate();
                }
            }
            connection.commit();
        }catch(Exception ex)
        {
            ex.printStackTrace();
        }finally {
            closeCnn(rs);
            closeCnn(pstmt);
            closeCnn(connection);
        }
    }
    public int convertToSecond(String duration)
    {
        try {
            String[] param = duration.split(":");
            return Integer.parseInt(param[0]) * 60 * 60 + Integer.parseInt(param[1]) * 60 + Integer.parseInt(param[2]);
        }catch(Exception ex)
        {
            return 0;
        }

    }
    public void closeCnn(Connection cn) {
        try {
            if (cn != null)
                cn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void closeCnn(PreparedStatement cn) {
        try {
            if (cn != null)
                cn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void closeCnn(ResultSet cn) {
        try {
            if (cn != null)
                cn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void closeCnn(CallableStatement cn) {
        try {
            if (cn != null)
                cn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
