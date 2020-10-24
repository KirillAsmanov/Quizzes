   
   function submitForm1()
   {
      var regexp;
      var Editbox2 = document.getElementById('Editbox2');
      if (!(Editbox2.disabled || Editbox2.style.display === 'none' || Editbox2.style.visibility === 'hidden'))
      {
         if (Editbox2.value == "")
         {
            alert("Недопустимая длина ответа");
            Editbox2.focus();
            return false;
         }
         if (Editbox2.value.length < 3)
         {
            alert("Недопустимая длина ответа");
            Editbox2.focus();
            return false;
         }
      }
      var Editbox3 = document.getElementById('Editbox3');
      if (!(Editbox3.disabled || Editbox3.style.display === 'none' || Editbox3.style.visibility === 'hidden'))
      {
         if (Editbox3.value == "")
         {
            alert("Недопустимая длина ответа");
            Editbox3.focus();
            return false;
         }
         if (Editbox3.value.length < 3)
         {
            alert("Недопустимая длина ответа");
            Editbox3.focus();
            return false;
         }
      }
      var Editbox4 = document.getElementById('Editbox4');
      if (!(Editbox4.disabled || Editbox4.style.display === 'none' || Editbox4.style.visibility === 'hidden'))
      {
         if (Editbox4.value == "")
         {
            alert("Недопустимая длина ответа");
            Editbox4.focus();
            return false;
         }
         if (Editbox4.value.length < 3)
         {
            alert("Недопустимая длина ответа");
            Editbox4.focus();
            return false;
         }
      }
      var TextArea1 = document.getElementById('TextArea1');
      if (!(TextArea1.disabled || TextArea1.style.display === 'none' || TextArea1.style.visibility === 'hidden'))
      {
         if (TextArea1.value == "")
         {
            alert("Недопустимая длина вопроса");
            TextArea1.focus();
            return false;
         }
         if (TextArea1.value.length < 3)
         {
            alert("Недопустимая длина вопроса");
            TextArea1.focus();
            return false;
         }
      }
      return true;
   }
   