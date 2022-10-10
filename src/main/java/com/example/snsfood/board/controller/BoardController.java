package com.example.snsfood.board.controller;

import com.example.snsfood.Reply.dto.ReplyDto;
import com.example.snsfood.Reply.model.ReplyParam;
import com.example.snsfood.Reply.service.ReplyService;
import com.example.snsfood.board.dto.BoardDto;
import com.example.snsfood.board.model.BoardInput;
import com.example.snsfood.board.model.BoardParam;
import com.example.snsfood.board.service.BoardService;
import com.example.snsfood.main.BaseController;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;


@RequiredArgsConstructor
@Controller
@Slf4j
public class BoardController extends BaseController {

    private final BoardService boardService;
    private final ReplyService replyService;

    @GetMapping("/board/index")
    public String totalList(Model model, BoardParam parameter) {


        return "board/index";
    }


    @GetMapping("/board/freeBoard")
    public String freeList(Model model, BoardParam parameter) {
        List<BoardDto> boardDtoList = boardService.list(parameter);
        long totalCount = 0;

        if (!CollectionUtils.isEmpty(boardDtoList)) {
            totalCount = boardDtoList.get(0).getTotalCount();
        }
        String queryString = parameter.getQueryString();
        String pagerHtml = getPagerHtml(totalCount, parameter.getPageSize(), parameter.getPageIndex(), queryString, parameter.getSearchType(), parameter.getSearchValue());
        model.addAttribute("list", boardDtoList);
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("pager", pagerHtml);



        return "board/freeBoard";

    }

    @GetMapping(value = {"/board/write", "/board/edit"})
    public String write(Model model, HttpServletRequest request, BoardInput parameter){
        boolean editMode = request.getRequestURI().contains("/edit");
        BoardDto detail = new BoardDto();

        if (editMode) {
            long id = parameter.getId();
            BoardDto boardDto = boardService.getById(id);
            if (boardDto == null) {
                model.addAttribute("message", "게시글 정보가 존재하지 않습니다.");
                return "common/error";
            }
            detail = boardDto;
        }
        model.addAttribute("editMode",editMode);
        model.addAttribute("detail", detail);

        return "board/write";
    }


    @PostMapping(value = {"/board/write", "/board/edit"})
    public String writeSubmit(Model model, HttpServletRequest request, BoardInput parameter,
                              Principal principal, MultipartFile file) {

        String categoryCode = request.getParameter("categoryCode");

        String saveFilename = "";
        String urlFilename = "";

        System.out.println(parameter.getId());
        System.out.println(parameter.getUserId());
        System.out.println(parameter.getCategoryCode());

        if (file != null) {

            String originalFilename = file.getOriginalFilename();
            String baseLocalPath = "/Users/hominbag/Desktop/dev/snsfood/files";
            String baseUrlPath = "/files";

            String[] arrFilename = getNewSaveFile(baseLocalPath, baseUrlPath, originalFilename);

            saveFilename = arrFilename[0];
            urlFilename = arrFilename[1];

            try {
                File newFile = new File(saveFilename);
                FileCopyUtils.copy(file.getInputStream(), new FileOutputStream(newFile));
            } catch (IOException e) {
                log.info("##################################################");
                log.info(e.getMessage());
            }
        }

        parameter.setFileName(saveFilename);
        parameter.setUrlFileName(urlFilename);

        String userId = principal.getName();
        parameter.setUserId(userId);
        boolean editMode = request.getRequestURI().contains("/edit");


        if (editMode) {
            long id = parameter.getId();
            BoardDto boardDto = boardService.getById(id);

            if (boardDto == null) {
                model.addAttribute("message", "게시글 정보가 존재하지 않습니다.");
                return "common/error";
            }
            boolean result = boardService.set(parameter);
        } else {
            boolean result = boardService.add(parameter);
        }

        return "redirect:/board/freeBoard";
    }







    //파일 저장
    private String[] getNewSaveFile(String baseLocalPath, String baseUrlPath, String originalFilename) {

        LocalDate now = LocalDate.now();

        String[] dirs = {
                String.format("%s/%d/", baseLocalPath, now.getYear()),
                String.format("%s/%d/%02d/", baseLocalPath, now.getYear(), now.getMonthValue()),
                String.format("%s/%d/%02d/%02d/", baseLocalPath, now.getYear(), now.getMonthValue(), now.getDayOfMonth())};

        String urlDir = String.format("%s/%d/%02d/%02d/", baseUrlPath, now.getYear(), now.getMonthValue(), now.getDayOfMonth());

        for (String dir : dirs) {
            File file = new File(dir);
            if (!file.isDirectory()) {
                file.mkdir();
            }
        }

        String fileExtension = "";
        if (originalFilename != null) {
            int dotPos = originalFilename.lastIndexOf(".");
            if (dotPos > -1) {
                fileExtension = originalFilename.substring(dotPos + 1);
            }
        }

        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        String newFilename = String.format("%s%s", dirs[2], uuid);
        String newUrlFilename = String.format("%s%s", urlDir, uuid);
        if (fileExtension.length() > 0) {
            newFilename += "." + fileExtension;
            newUrlFilename += "." + fileExtension;
        }

        return new String[]{newFilename, newUrlFilename};
    }


    @GetMapping("/board/{id}")
    public String boardDetail(Model model, BoardParam parameter, Principal principal, ReplyParam replyParam){
        BoardDto detail = boardService.boardDetail(parameter.getId());
        model.addAttribute("detail", detail);
        String userId = principal.getName();
        model.addAttribute("userId", userId); //댓글 유저아이디 주기
        System.out.println(replyParam);
        long bid = parameter.getId();
        replyParam.setBid(bid);
        List<ReplyDto> replyDtoList = boardService.replylist(replyParam);
        long totalCount = 0;
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"+replyDtoList.toString()+"@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        if (!CollectionUtils.isEmpty(replyDtoList)) {
            totalCount = replyDtoList.get(0).getTotalCount();
        }
        model.addAttribute("list", replyDtoList);
        model.addAttribute("totalCount", totalCount);


        return "board/detailBoard";
    }


    @DeleteMapping("/board/delete")
    public String del(Model model, HttpServletRequest request, BoardInput parameter) {
        boolean result = boardService.del(parameter.getId());

        return "redirect:/board/freeBoard";
    }












}
