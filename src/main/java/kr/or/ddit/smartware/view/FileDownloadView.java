package kr.or.ddit.smartware.view;

import java.io.File;
import java.io.FileInputStream;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.AbstractView;
import kr.or.ddit.smartware.post.model.PostFile;

public class FileDownloadView extends AbstractView{

	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		PostFile postfile = (PostFile) model.get("postfile");
		
		response.setHeader("content-disposition", "attachment;filename="+postfile.getFile_nm());
		response.setContentType("application/octet-stream");	//바이너리로 요청이 전송됨
		
		File file = new File(postfile.getRealfilepath());
		
		FileInputStream fis = new FileInputStream(file);
		ServletOutputStream sos = response.getOutputStream();
		
		byte[] buffer = new byte[512];
		int len = 0;
		while((len = fis.read(buffer, 0, 512)) != -1)
			sos.write(buffer, 0, len);
		
		sos.close();
		fis.close();
	}
}
