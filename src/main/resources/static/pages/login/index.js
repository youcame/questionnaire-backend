const onLogin = () => {
  let params = {
    userAccount: $('#userAccount').val().trim(),
    password: $('#password').val().trim()
  }
  if (!params.userAccount) return alert('请输入用户名！')
  if (!params.password) return alert('请输入密码！')
  $.ajax({
    url: API_BASE_URL + '/user/login',
    type: "POST",
    data: JSON.stringify(params),
    dataType: "json",
    contentType: "application/json",
    success(res) {
      if (res!=null) {
        $util.setItem('userInfo', res.data)
        location.href = "/pages/questionnaire/index.html"
      } else {
        alert(res)
      }
    }
  })
}

