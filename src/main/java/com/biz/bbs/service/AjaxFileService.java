package com.biz.bbs.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.biz.bbs.mapper.FileDao;
import com.biz.bbs.model.BBsReqDto;
import com.biz.bbs.model.FileVO;


@Service
public class AjaxFileService {

	@Autowired
	FileDao fDao;
	
	
	
	/*
	 * 변수에 final 키워드가 붙으면
	 * 이 변수의 값을 변경할 수 없다.
	 */
	private final String upLoadFolder = "c:/bizwork/upload/";
	
	/*
	 * 여러개의 파일을 업로드 하는 method
	 */
	public List<FileVO> uploads(MultipartHttpServletRequest files) {
		List<MultipartFile> fileList = files.getFiles("files");
		List<FileVO> fileVOList = new ArrayList<FileVO>();
		for(MultipartFile file : fileList) {
			
			String saveName = this.upLoad(file);
			
			fileVOList.add(FileVO.builder()
			.file_origin_name(file.getOriginalFilename())
			.file_name(saveName).build());
		}
		return fileVOList;
	}
	
	/*
	 * 한개의 파일을 업로드한느 메서드
	 */
	
	public String upLoad(MultipartFile file) {
		
		if(file.isEmpty() || file == null) return null;
		
		String originName = file.getOriginalFilename();
		String uuString = UUID.randomUUID().toString();
		String savaName = uuString + "_" + originName;
		
		File saverDir = new File(upLoadFolder);
		if(!saverDir.exists()) {
			saverDir.mkdir();
		}
		
		File saveFile = new File(upLoadFolder,savaName);
		try {
			file.transferTo(saveFile);
		} catch (IllegalStateException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return savaName;
	}

public boolean file_delete(long file_seq) {
		
		// 1. 파일 정보 추출
		FileVO fileVO = fDao.findBySeq(file_seq);
		// 2. 파일의 물리적 경로 생성
		File delFlie = new File(upLoadFolder, fileVO.getFile_name());
		
		// 3. 파일이 있는지 확인 한 후 
		if(delFlie.exists()) {
			// 4. 파일 삭제
			delFlie.delete();
			
			// 5. DB 정보 삭제
			fDao.delete(file_seq);
			
			return true;
		}
		return false;
	}

public int insert(BBsReqDto bbsReqDto) {
	
	List<String> bbs_files = bbsReqDto.getBbs_files();
	for(String file_name : bbs_files) {
		long bbs_seq = bbsReqDto.getBbs_seq();
		// UUID를 제거하고 orgin 이름을 추출
		String file_origin_name = file_name.substring(37);
		FileVO fVO = FileVO.builder()
				.file_name(file_name)
				.file_origin_name(file_origin_name)
				.file_bbs_seq(bbs_seq).build();
		fDao.insert(fVO);
		
		
	}
	return 0;
}
	

	
	
}
