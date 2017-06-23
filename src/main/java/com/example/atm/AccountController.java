package com.example.atm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

/**
 * Created by student on 6/23/17.
 */
@Controller
public class AccountController {

        @Autowired
        private AccountRepository accountRepository;
        @Autowired
        private UserRepository userRepository;
        @Autowired
        private  TransactionRepository transactionRepository;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String getHome(Model model, String error, String logout){
        model.addAttribute(new User());
        return "login";
    }

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public String processingLogin(Model model, @Valid User user, BindingResult bindingResult){

        if (bindingResult.hasErrors()) {
            return "login";
        }

        User user1 = new User();
        if (userRepository.existsByUserName(user.getUserName())) {

             user1 = userRepository.findByUserName(user.getUserName());
        }

        if (user1.getPassword().equals(user.getPassword())){
            //userRepository.save(user);

            return "redirect:/deposit";
        }
        model.addAttribute("user", new User());
        return "login";
    }
    @RequestMapping(value = "/transaction",method = RequestMethod.POST)
    public String processingTransaction(@ModelAttribute Transaction transaction){
        transaction.setBalance(transaction.getBalance()+transaction.getAmount());
        transactionRepository.save(transaction);
        return "redirect:/transaction";
    }

    @RequestMapping(value = "/transaction", method = RequestMethod.GET)
    public String toTrasactionDisplay(@ModelAttribute Account account, Model model){
        Iterable<Account> accountIterable = accountRepository.findAll();
        model.addAttribute("account", accountIterable);
        return "transactionHistory";
    }


   /* @RequestMapping(value = "/transactionHistory", method = RequestMethod.GET)
    public String toDisplay(@ModelAttribute Account account, Model model){
        Iterable<Account> accountIterable = accountRepository.findAll();
        model.addAttribute("account", accountIterable);
        return "transactionHistory";
    }
*/

    @RequestMapping(value = "/deposit", method = RequestMethod.GET)
        public String getDepositForm(Model model){

            model.addAttribute(new Account());
            return "deposit";
        }

        @RequestMapping(value = "/deposit",method = RequestMethod.POST)
        public String processingForm(@ModelAttribute Account account){
            accountRepository.save(account);
            return "redirect:/transactionHistory";
        }

        @RequestMapping(value = "/transactionHistory", method = RequestMethod.GET)
        public String toDisplay(@ModelAttribute Account account, Model model){
            Iterable<Account> accountIterable = accountRepository.findAll();
            model.addAttribute("account", accountIterable);
            return "transactionHistory";
        }
    }
