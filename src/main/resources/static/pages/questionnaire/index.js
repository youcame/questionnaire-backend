$(document).ready(function() {
  $('#headerUsername').text($util.getItem('userInfo').userAccount);
  handleHeaderLoad();
  fetchProjectList();
});

let projectList = [];

const fetchProjectList = () => {
  $.ajax({
    url: API_BASE_URL + '/project/search',
    type: 'GET',
    dataType: 'json',
    contentType: 'application/json',
    success(res) {
      projectList = res;
      $('#content').html('');

      res.map(item => {
        $('#content').append(`
          <div class="list">
            <div class="list-header">
              <div>${item.projectName}</div>
              <div>
                <button type="button" class="btn btn-link" onclick="onCreateQuestionnaire()">创建问卷</button>
                <button type="button" class="btn btn-link" onclick="onSeeProject('${item.id}')">查看</button>
                <button type="button" class="btn btn-link" onclick="onEditProject('${item.id}')">编辑</button>
                <button type="button" class="btn btn-link" onclick="onDelProject('${item.id}')">删除</button>
              </div>
            </div>
            <div class="list-footer">
              <div>暂无调查问卷或问卷已过期</div>
            </div>
          </div>
        `)
      })
    }
  });
};

const onCreateProject = () => {
  location.href = '/pages/createProject/index.html';
};

const onCreateQuestionnaire = () => {
  location.href = '/pages/createQuestionnaire/index.html';
};

const onSeeProject = (id) => {
  $util.setPageParam('seeProject', id);
  location.href = '/pages/seeProject/index.html';
};

const onEditProject = (id) => {
  let project = projectList.find(item => item.id === id);
  $util.setPageParam('editProject', project);
  location.href = '/pages/editProject/index.html';
};

const onDelProject = (pid) => {
  let state = confirm('确认删除该项目吗？');
  if (state) {
    let params = pid; // 直接将整数值赋给参数
    $.ajax({
      url: API_BASE_URL + '/project/delete',
      type: 'POST',
      data: params, // 不再需要将参数转为JSON字符串
      dataType: 'json',
      contentType: 'application/json',
      success(res) {
        fetchProjectList();
      }
    });
  }
};
