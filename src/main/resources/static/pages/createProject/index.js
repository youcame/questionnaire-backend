onload = () => {
  $('#headerUsername').text($util.getItem('userInfo').userAccount)
  $('#headerDivB').text('创建项目')
}

const handleCreateProject = () => {
  let params = {
    createdBy: $util.getItem('userInfo').userAccount,
    lastUpdatedBy: $util.getItem('userInfo').userAccount,
    projectName: $('#projectName').val(),
    projectDescription: $('#projectDescription').val()
  }
  if (!params.projectName) return alert('项目名称不能为空！')
  if (!params.projectDescription) return alert('项目描述不能为空！')
  $.ajax({
    url: API_BASE_URL + '/project/create',
    type: "POST",
    data: JSON.stringify(params),
    dataType: "json",
    contentType: "application/json",
    success() {
      alert('创建成功！')
    }
  })
}
