function taskManager(){
    let tasks = [];
    let taskID = 0;
    let priority = {
        high: [],
        medium: [],
        low: []

    }
    return {
        addTask: (name, priority) => {
            task = {
                id: taskID ++,
                Name: name,
                priority: priority
            }
            tasks.push(task);
        },
        removeTask: (Taskid) => {
            /*this.task.array.forEach(task => {
                if (task.id === Taskid){
                    index = task.id;
                    this.task.splice(index, 1);
                }
            });*/

            const index = tasks.findIndex(task => task.id === Taskid);

            if (index === -1){
                console.log("Task not found")
            }
            else {
                return tasks.splice(index, 1);
            }
        },
        GetTask: () => {
            let response = {
                1: [],
                2: [],
                3: []
            }

            tasks.forEach(element => {
                response[element.priority].push(element)
            });

            console.log( [...response[1], ...response[2], ...response[3]] );
        }

    }
}

x = taskManager();
x.addTask("Hello", 1);
x.addTask("Yugen", 2);
x.addTask("Limbu", 3);
x.addTask("Hi", 1);
x.GetTask();
