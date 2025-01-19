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
         return "redirect:/items";
    }


    @GetMapping("/addForm")
    public String itemAdd(@ModelAttribute("item") Item item){
        System.out.println("에드폼");

        return "addForm";


    }



}
