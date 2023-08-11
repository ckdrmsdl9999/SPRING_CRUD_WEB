package project.Controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import project.Repository.ItemRepository;
import project.domain.Item;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemRepository itemRepository;

    @GetMapping("/items")
    public String itemList(Model model){
    List<Item> items = itemRepository.findAll();
    model.addAttribute("items",items);
        System.out.println("itemlist호출");
        return "items";

    }
    @PostMapping("/items2")
    public String itemSave(@ModelAttribute Item item, BindingResult bindingResult){
        //@Valid @ModelAttribute작동하기위해 다시알아보기
        if (bindingResult.hasErrors()) {
            return "redirect:/items";
        }

        System.out.println(item.getItemname()+" "+item.getPrice()+" "+item.getQuantity());
        itemRepository.save(item);
//
//        List<Item> items = itemRepository.findAll();
//        model.addAttribute("items",items);

//        return "items";//"/"는안되네 왜 postmapping에서 getmapping/items로 넘어가는값이없으니까->finall잇는데?
        //redirect안하면 로그인도 변동이없는건가? html파일만호출하고  -->지금보니 네트워크에서 요청url은 items2임, pritn문도 안보이고 return에선
        // getmapping의 컨트롤러단에는안가는건가? return문은 컨트롤러호출이아니라 ..
        //그럼 이런데서 return loginhome하면 쿠키값안뜰수도있게되나? 확인해보고      그리고 redirect인경우에는 컨트롤러호출? get요청으로? 다시고민해보기
         return "redirect:/items";
    }


    @GetMapping("/addForm")
    public String itemAdd(@ModelAttribute("item") Item item){
        System.out.println("에드폼");
//        itemRepository.save(item);
//        List<Item> items = itemRepository.findAll();
//        model.addAttribute("items",items);

        return "addForm";


    }



}
