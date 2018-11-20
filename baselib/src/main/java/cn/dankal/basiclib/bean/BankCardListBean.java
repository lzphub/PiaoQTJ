package cn.dankal.basiclib.bean;

import java.io.Serializable;
import java.util.List;

public class BankCardListBean implements Serializable {

    private List<data> cards;

    public List<data> getCards() {
        return cards;
    }

    public void setCards(List<data> cards) {
        this.cards = cards;
    }

   public class data implements Serializable{
       private String card_number;
       private String bank_name;

        public String getCard_number() {
            return card_number;
        }

        public void setCard_number(String card_number) {
            this.card_number = card_number;
        }

        public String getBank_name() {
            return bank_name;
        }

        public void setBank_name(String bank_name) {
            this.bank_name = bank_name;
        }
    }

}
