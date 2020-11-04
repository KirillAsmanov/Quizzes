   
   function submitForm1()
   {
      var regexp;
      var Editbox1 = document.getElementById('Editbox1');
      if (!(Editbox1.disabled || Editbox1.style.display === 'none' || Editbox1.style.visibility === 'hidden'))
      {
         regexp = /^[A-Za-zАБВГДЕЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯабвгдежзийклмнопрстуфхчцшщъыьэюя \t\r\n\f0-9-]*$/;
         if (!regexp.test(Editbox1.value))
         {
            alert("Недопустимый символ");
            Editbox1.focus();
            return false;
         }
         if (Editbox1.value == "")
         {
            alert("Название не должно быть пустым");
            Editbox1.focus();
            return false;
         }
         if (Editbox1.value.length < 3)
         {
            alert("Число символов в названии должно быть больше 3");
            Editbox1.focus();
            return false;
         }
         if (Editbox1.value.length > 30)
         {
            alert("Число символов в названии не должно превышать 30");
            Editbox1.focus();
            return false;
         }
      }
      var TextArea1 = document.getElementById('TextArea1');
      if (!(TextArea1.disabled || TextArea1.style.display === 'none' || TextArea1.style.visibility === 'hidden'))
      {
         regexp = /^[A-Za-zАБВГДЕЖЗИЙКЛМНОПРСТУФХЧЦШЩЪЫЬЭЮЯабвгдежзийклмнопрстучфхцшщъыьэюя \t\r\n\f0-9-]*$/;
         if (TextArea1.value.length != 0 && !regexp.test(TextArea1.value))
         {
            alert("Недопустимый символ");
            TextArea1.focus();
            return false;
         }
         if (TextArea1.value.length < 3)
         {
            alert("Число символов в описании должно быть больше 3");
            TextArea1.focus();
            return false;
         }
         if (TextArea1.value.length > 110)
         {
            alert("Число символов в описании не должно превышать 110");
            TextArea1.focus();
            return false;
         }
      }
      return true;
   }
   