package core.spring.security.controller.user;

import core.spring.security.dto.UserDto;
import core.spring.security.service.user.UserService;
import core.spring.security.service.user.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @GetMapping(value="/mypage")
    public String myPage() throws Exception {

        return "user/mypage";
    }

    @GetMapping("/users")
    public String join() {
        return "user/login/register";
    }

    @PostMapping("/users")
    public String joinProc(@Valid UserDto.RequestDto userDto, BindingResult bindingResult, Model model){

        //검증
        if(bindingResult.hasErrors()){
            //회원가입 실패시 입력 데이터 유지
            model.addAttribute("userDto", userDto);

            /* 유효성 검사를 통과하지 못한 필드와 메시지 핸들링*/
            Map<String,String> errorMap = new HashMap<>();

            for(FieldError error : bindingResult.getFieldErrors()){
                errorMap.put("valid_"+error.getField(), error.getDefaultMessage());
                log.info("회원 가입 실패 ! error message ==>" + error.getDefaultMessage());
            }

            /*Model 에 담아 view resolve*/
            for(String key : errorMap.keySet()){
                model.addAttribute(key,errorMap.get(key));
            }
            //회원가입 페이지로 리턴
            return "user/login/register";
        }

        //전부 통과시 회원가입 진행
        userService.userJoin(userDto);
        log.info("회원가입 성공");
        return "redirect:/";
    }

    @GetMapping("/login")
    public String login(){
        return "user/login/login";
    }

}