# Recipe App
## Projenin Amacı
Yemek tarifi uygulaması yapmaktır. Uygulamaya kayıt olan User rolündeki kullanıcılar yemek tariflerine bakabilecektir.
Bu tariflere yorum, beğeni ve yemeğin özelliklerine göre gerekli filtreleme işlemleri yapılmak amaçlanmıştır.
Sadece Admin rolünde kayıt olan yani yetki verilmiş kullanıcılar yemek tarifi ekleyebilir, silebilir, güncelleyebilir.
### Yapılan aksiyonları aşağıda paylaşacağım.

## user-service

![userRegister](https://github.com/enesdoganoglu/RecipeApp/assets/117980244/e3a6ecba-d74b-42b4-a96e-e403ca8f29a6)

![userRegisterPostgre](https://github.com/enesdoganoglu/RecipeApp/assets/117980244/ef6c2e3d-cf3a-4f76-aead-b9bc7bf16c1a)

Burada yapılmak istenen giriş sayfasında kullanıcın gerekli bilgilerini alarak veritabanımıza kaydetmek <br>

![usermailactivasyın](https://github.com/enesdoganoglu/RecipeApp/assets/117980244/bef1da73-d689-4864-b66f-671a7e97b512)
![useractivatestatus](https://github.com/enesdoganoglu/RecipeApp/assets/117980244/52f584e1-a9ac-45e7-95e1-e58a1d5dc53c)
![useractivatestatusPostgre](https://github.com/enesdoganoglu/RecipeApp/assets/117980244/57fafc6a-bbd4-4a5f-83c4-c2daaf0b6e3a)

Kullanıcının verdiği maile aktivasyon kodu gönderilerek bu aktivasyon kodunu sisteme verip kişinin doğruluğu teyit ettik. PENDING'den ACTIVE'e çekilmiş bulunduk.


