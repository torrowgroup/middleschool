package com.torrow.school.controller.manager;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.torrow.school.base.BaseController;
import com.torrow.school.entity.TbCategory;
import com.torrow.school.entity.TbResource;

/**
 * @author 安李杰
 *
 * @2017年10月10日上午8:04:46
 */
@Controller
@RequestMapping("/general")
public class GeneralController extends BaseController{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * @param currentPage
	 * @param model
	 * @return 概括类的分页查看
	 */
	@RequestMapping("manageGeneral")
	public String manageGeneral(@RequestParam(value="currentPage",defaultValue="1")int currentPage,Model model){
		model.addAttribute("pagemsg",resourceService.findingByPaging(currentPage,1));//回显分页数据
		return "admin/managegeneral";
	}
	
	/**
	 * @param model
	 * @param id
	 * @return 得到概括类的一条记录
	 */
	@RequestMapping("selectOnegeneral")
	public String selectOnegeneral(Model model,int id) {
		TbResource tb=resourceService.selectByPrimaryKey(id);
		model.addAttribute("general",tb);
		return "admin/general/updategeneral";
	}
	
	/**
	 * @param model
	 * @param id
	 * @param reTitle
	 * @param reContent
	 * @param caName
	 * @return 对于概括类进行修改的操作
	 */
	@RequestMapping("updateGeneral")
	public String updateGeneral(Model model,int id,String reTitle,String reContent){
		TbResource tb=resourceService.selectByPrimaryKey(id);
		TbResource record=new TbResource(tb.getReId(),tb.getCaId(),tb.getCaName(),reTitle,tb.getReIssuer(),tb.getReIssuingdate(),reContent);
		int i=resourceService.updateByPrimaryKey(record);
		if(i!=0) {
			model.addAttribute("message","修改成功");
		}else{
			model.addAttribute("message","修改失败");
		}
		return this.manageGeneral(1, model);	
	}
	
	/**
	 * @return 删除概括类
	 */
	@RequestMapping("deleteGeneral")
	public String deleteGeneral(Model model,int id){
		int i=resourceService.deleteByPrimaryKey(id);
		if(i!=0) {
			model.addAttribute("message","删除成功");
		}else {
			model.addAttribute("message","删除失败");
		}
		return this.manageGeneral(1, model);
	}
	
	@RequestMapping("manageGeneralJumping")
	public String manageGeneralJumping(){
		return "admin/general";
	}
	
	/**
	 * @param model
	 * @return 查看学校简介
	 */
	@RequestMapping("checkSchoolIntroduction")
	public String schoolIntroduction(Model model) {
		List<TbResource> tbResource=resourceService.selectAll();
		for(TbResource item:tbResource){
			if(item.getCaName().equals("学校简介")){
				model.addAttribute("introduction",item);
			}
		}
		
		return "admin/general/checkschoolIntroduction";
	}
	
	
	/**
	 * @param model
	 * @return 对于资源类的添加跳转
	 */
	@RequestMapping("generalJumping")
	public String resourcejumping(Model model){
		return "admin/addgeneral";
	}
	
	/**
	 * @param model
	 * @param reTitle
	 * @param reContent
	 * @param caName
	 * @return 概括类的添加
	 */
	@RequestMapping("addGeneral")
	public String addGeneral(Model model,String reTitle,String reContent,String caName){
		List<TbCategory> tbCategory=categoryService.selectAll();
		for(TbCategory item:tbCategory){
				if(caName.equals(item.getCaName())){
					TbResource record=new TbResource(item.getCaId(),caName,reTitle,reContent);
					resourceService.insert(record);
				}
			
		}
		return this.manageGeneral(1, model);
	}
	
}
