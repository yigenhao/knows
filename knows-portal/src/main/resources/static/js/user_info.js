let userApp=new Vue({
    el:"#userApp",
    data:{
        user:{}
    },
    methods:{
        loadCurrentUser:function () {
            axios({
                url:"v1/users/me",
                method:"get",
            }).then(function (response) {
                userApp.user=response.data;

            })

        }
    },
    //created表示页面加载完毕后自动调用
    created:function () {
        this.loadCurrentUser();

    }
});