$(document).ready(function() {
  $('#headerDivB').text('项目详情');

  let projectId = $util.getPageParam('seeProject');
  console.log(projectId, 'projectId');
  fetchProjectInfo(projectId);
});

const fetchProjectInfo = (id) => {
  let params = {
    id: id
  };
  $.ajax({
    url: API_BASE_URL + '/project/findProject',
    type: 'POST',
    data: id,
    dataType: 'json',
    contentType: 'application/json',
    success(res) {
      let info = res;
      console.log(info, 'res');
      $('#projectName').text(info.projectName);
      $('#createTime').text(info.createTime);
      $('#projectDescription').text(info.projectDescription);
    }
  });
};
