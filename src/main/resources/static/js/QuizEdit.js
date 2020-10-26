   
   function submitForm1()
   {
      var regexp;
      var Editbox1 = document.getElementById('Editbox1');
      if (!(Editbox1.disabled || Editbox1.style.display === 'none' || Editbox1.style.visibility === 'hidden'))
      {
         regexp = /^[A-Za-zАБВГДЕЖЗИЙКЛМНОПРСТУФХЦШЩЪЫЬЭЮЯабвгдежзийклмнопрстуфхцшщъыьэюя \t\r\n\f0-9-]*$/;
         if (!regexp.test(Editbox1.value))
         {
            alert("Недопустимая длина названия");
            Editbox1.focus();
            return false;
         }
         if (Editbox1.value == "")
         {
            alert("Недопустимая длина названия");
            Editbox1.focus();
            return false;
         }
         if (Editbox1.value.length < 3)
         {
            alert("Недопустимая длина названия");
            Editbox1.focus();
            return false;
         }
         if (Editbox1.value.length > 30)
         {
            alert("Недопустимая длина названия");
            Editbox1.focus();
            return false;
         }
      }
      var TextArea1 = document.getElementById('TextArea1');
      if (!(TextArea1.disabled || TextArea1.style.display === 'none' || TextArea1.style.visibility === 'hidden'))
      {
         regexp = /^[A-Za-zАБВГДЕЖЗИЙКЛМНОПРСТУФХЦШЩЪЫЬЭЮЯабвгдежзийклмнопрстуфхцшщъыьэюя \t\r\n\f0-9-]*$/;
         if (TextArea1.value.length != 0 && !regexp.test(TextArea1.value))
         {
            alert("Недопустимая длина описания");
            TextArea1.focus();
            return false;
         }
         if (TextArea1.value.length < 3)
         {
            alert("Недопустимая длина описания");
            TextArea1.focus();
            return false;
         }
         if (TextArea1.value.length > 110)
         {
            alert("Недопустимая длина описания");
            TextArea1.focus();
            return false;
         }
      }
      return true;
   }
   