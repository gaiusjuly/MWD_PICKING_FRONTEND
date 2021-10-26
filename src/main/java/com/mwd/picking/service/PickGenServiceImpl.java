package com.mwd.picking.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mwd.picking.persitence.mybatis.PickGenRepository;
import com.mwd.picking.model.GroupHeader;
import com.mwd.picking.model.Pick;
import com.mwd.picking.web.PickGenController;

@Service
@Transactional
public class PickGenServiceImpl {
	private static final Logger logger = LoggerFactory.getLogger(PickGenController.class);
	@Autowired
	 private PickGenRepository repository;
	
	public List<GroupHeader> generateGroupKey(GroupHeader data) throws Exception {
		
		 System.err.println("PickGenService has started");
		 List<GroupHeader> gh = selectGroupHeader(data);
		 if(gh.size()>0)return gh;
		 else {
			String seq = selectGroupSeq();
			String groupkey = data.getDeliDate()+""+data.getPickerId()+""+this.setLPad(seq,5,"0");
			data.setGroupkey(groupkey);
			data.setStatus("10");
//			System.err.println("generate group key start");
//			System.err.println(header.getGroupkey());
//			System.err.println(header.getPickerId());
//			System.err.println(header.getStatus());
//			System.err.println(header.getPickArea());
			
			System.err.println("insertGroupHeader start");
			insertGroupHeader(data);
			System.err.println("insertGroupHeader end");
			
//			String delinos = String.join(",",data.getDelinos());
			
			System.err.println("updateDetail start");
			for(int i=0;i<data.getDelinos().length;i++) {
				GroupHeader gdata = new GroupHeader();
				gdata.setDeliNo(data.getDelinos()[i]);
				gdata.setPickArea(data.getPickArea());
				List<GroupHeader> glist = this.selectQty(gdata);
				for(int j=0;j<glist.size();j++) {
					gdata.setDeliDetailSeq(glist.get(j).getDeliDetailSeq());
					gdata.setQty(glist.get(j).getQty());
					gdata.setGroupkey(data.getGroupkey());
					gdata.setStatus(data.getStatus());
					gdata.setMasterToteArea(new Integer(i).toString());
					updateTrDetail(gdata);
				}
			  data.setDeliNo(data.getDelinos()[i]);
			  data.setQty(data.getQtys()[i]);
			  System.err.println("groupkey "+ data.getGroupkey());
			  System.err.println("delino "+ data.getDeliNo());
			  System.err.println("pickerId "+ data.getPickerId());
			  insertGroupDetail(data);
			  updateTrHeader(data);
			  updateTrHeaderPost(data.getDeliNo());
			}
			
			gh = selectGroupHeader(data);
			System.err.println("after generate groupkey "+gh.get(0));
			return gh;
		 }
		
	}
	
	private List<GroupHeader> selectQty(GroupHeader data ) throws Exception
    { 
		return repository.selectQty(data);
    }
	
	public List<GroupHeader> selectGroupHeader(GroupHeader header ) throws Exception
    { 
		return repository.selectGroupHeader(header);
    }
	
	public String selectGroupSeq() throws Exception
    { 
		return repository.selectGroupSeq();
    }
	
	public int insertGroupHeader(GroupHeader header) throws Exception
    { 
		return repository.insertGroupHeader(header);
    }
	
	public int insertGroupDetail(GroupHeader header) throws Exception
    { 
		return repository.insertGroupDetail(header);
    }
	
	public int updateTrHeader(GroupHeader header) throws Exception
    { 
		return repository.updateTrHeader(header);
    }
	
	public int updateTrHeaderPost(String deliNo) throws Exception
    { 
		return repository.updateTrHeaderPost(deliNo);
    }
	
	public int updateTrDetail(GroupHeader header) throws Exception
    { 
		System.err.println("updateTrDetail start");
		return repository.updateTrDetail(header);
    }


	public int rollbacktote(GroupHeader header) throws Exception
	{
		repository.rollbackassigntotedetail(header);
		repository.rollbackassigntoteheader(header);
		String delinos[] = header.getDelinostr().split(",");

		for(int i=0;i<delinos.length;i++){
			Pick pick = new Pick();
			pick.setGroupkey(header.getGroupkey());
			pick.setDeliNo(delinos[i]);
			pick.setPickerId(header.getPickerId());
			repository.updateCompleteTrHeader(pick);
		}
		return 1;
	}

	public int rollbackpick(GroupHeader header) throws Exception
	{
		repository.rollbackpick(header);
		repository.rollbacktrdetail(header);
		repository.rollbackassigntotedetail(header);
		repository.rollbackassigntoteheader(header);

		String delinos[] = header.getDelinostr().split(",");
		for(int i=0;i<delinos.length;i++){
			Pick pick = new Pick();
			pick.setGroupkey(header.getGroupkey());
			pick.setDeliNo(delinos[i]);
			pick.setPickerId(header.getPickerId());
			repository.updateCompleteTrHeader(pick);
		}
		return 1;
	}

	private String setLPad(String strContext, int iLen, String strChar ) {
		String strResult = ""; 
		StringBuilder sbAddChar = new StringBuilder(); 
		for( int i = strContext.length(); i < iLen; i++ ) {
			sbAddChar.append( strChar ); 
			} 
		strResult = sbAddChar + strContext; 
		return strResult;
     }
	
}
