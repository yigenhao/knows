
let questionApp=new Vue({
    el:"#questionApp",
    data:{
        question:{}
    },
    methods:{
        //加载问题方法
        loadQuestion:function(){
            //获得url路径上?之后的内容
            let qid=location.search;
            if(!qid){
                //qid为空时进入if
                return;
            }
            //如果不为空把?去掉
            //?149
            //0123
            qid=qid.substring(1);
            //alert(qid);
            axios({
                url:"/v1/questions/"+qid, //  /v1/questions/149
                method:"get",
            }).then(function(response){
                questionApp.question=response.data;
                addDuration(questionApp.question);
            })
        }
    },
    created:function(){
        this.loadQuestion();
    }
})


let postAnswerApp=new Vue({
    el:"#postAnswerApp",
    data:{
        hasError:false,
        message:""
    },
    methods:{
        //上传回答方法
        postAnswer:function () {
            let content=$("#summernote").val();
            if(!content){
                this.hasError=true;
                this.message="回答内容不能为空";
                return;
            }
            //获得当前问题的id值
            let qid=location.search;
            if (!qid){
                this.hasError=true;
                this.message="问题id不能为空";
                return;
            }
            qid=qid.substring(1);
            //构建表单
            let form=new FormData();
            form.append("questionId",qid);
            form.append("content",content);
            axios({
                url:"/v1/answers",
                method:"post",
                data:form
            }).then(function (response) {
                console.log(response.data);
                response.data.duration="刚刚";
                //将新增成功的回答添加到回答中
                answersApp.answers.push(response.data);
                //重置富文本编辑器
                $("#summernote").summernote("reset");
                postAnswerApp.hasError=true;
                postAnswerApp.message="回答已提交！";


            })

        }
    }
})


let answersApp= new Vue({
    el:"#answersApp",
    data:{
        answers:[]
    },
    methods:{
        //采纳回答方法
        answerSolved:function(answerId,answer){
            //当前回答如果已被采纳，则终止
            if (answer.acceptStatus == 1){
                alert("当前回答已被采纳过了");
                return;
            }
            axios({
                url:"/v1/answers/"+answerId+"/solved",
                method:"get",
            }).then(function (response) {
                if (response.data=="ok"){
                    answer.acceptStatus=1;
                    alert("答案采纳成功!!!");
                }else {
                    alert(response.data);
                }

            })

        },
        //编辑评论方法
        updateComment:function(commentId,answerId,index,comments){
            //获得用户输入的内容
            let textarea=$("#editComment"+commentId+" textarea");
            let content=textarea.val();
            if (!content){
                return;
            }
            let form = new FormData();
            form.append("answerId",answerId);
            form.append("content",content);
            axios({
                url:"/v1/comments/"+commentId+"/update",
                method:"post",
                data:form
            }).then(function (response) {
                //修改评论response.data时修改之后的评论
                //我们需要修改成功的评论替换之前的评论
                Vue.set(comments,index,response.data);
                //将现在展开的编辑框折叠起来
                $("#editComment"+commentId).collapse("hide");

            })

        },
        //删除评论方法
        removeComment:function(commentId,index,comments){
            if (!commentId){
                return;
            }
            axios({
                url:"/v1/comments/" + commentId + "/delete",
                method:"get"
            }).then(function (response) {
                if (response.data=="ok"){
                    comments.splice(index,1);
                }else{
                    alert(response.data);
                }

            })

        },
        //上传评论方法
        postComment:function(answerId){
            if (!answerId){
                return;
            }
            //获取当前提交的评论的textarea对象
            let textarea=$("#addComment"+answerId+" textarea");
            let  content=textarea.val();
            if (!content){
                return;
            }
            //构建表单
            let form = new FormData();
            form.append("answerId",answerId);
            form.append("content",content);
            axios({
                url:"/v1/comments",
                method:"post",
                data:form
            }).then(function (response) {
                let comment=response.data;
                //遍历当前回答数组中所有回答
                let answers=answersApp.answers;
                for (let i=0;i<answers.length;i++){
                    //如果当前元素的id和本次评论的回答相同
                    if (answers[i].id=answerId){
                        //就吧新增的评论增加到
                        answers[i].comments.push(comment);
                        break;
                    }
                }
                //清空输入框
                textarea.val("");

            })

        },
        //显示所有回答
        loadAnswers:function () {
            let qid=location.search;
            if (!qid){
                return;
            }
            qid=qid.substring(1);
            axios({
                url:"/v1/answers/question/"+qid,
                method:"get"
            }).then(function (response) {
                console.log(response.data);
                answersApp.answers=response.data;
                answersApp.updateDuration();

            })

        },
        //显示持续时间方法
        updateDuration:function () {
            let answers=this.answers;
            for (let i=0;i<answers.length;i++){
                addDuration(answers[i]);
            }
        }
    },
    created:function () {
        this.loadAnswers();

    }
})