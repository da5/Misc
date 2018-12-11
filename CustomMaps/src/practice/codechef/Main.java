package practice.codechef;

public class Main {

    static BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) {

        String inputLine = "";

        try{
            inputLine = bufferedReader.readLine();
            for(int i = Integer.parseInt(inputLine); i>0; i--){
                inputLine = bufferedReader.readLine();
                schedule(inputLine);
            }

        }catch (IOException e){

        }
    }

    public static void schedule(String str) throws IOException{
        String[] parts = str.split(" ");
        int trainers = Integer.parseInt(parts[0]);
        int days = Integer.parseInt(parts[1]);

        List[] listDays = new List[days+1];
        for(int i =1; i<=days; i++){
            listDays[i] = new ArrayList<Trainer>();
        }

        for(int i =0; i<trainers; i++){
            String line = bufferedReader.readLine();
            parts = line.split(" ");
            int day = Integer.parseInt(parts[0]);
            int lectures = Integer.parseInt(parts[1]);
            int sadness = Integer.parseInt(parts[2]);
            if(lectures > 0){
                listDays[day].add(new Trainer(day, new Long(lectures), new Long(sadness)));
            }

        }
        Queue<Trainer> queue = new PriorityQueue<>(trainers, new Comparator<Trainer>() {
            @Override
            public int compare(Trainer o1, Trainer o2) {
                if(o1.lectures == 0 && o2.lectures == 0){
                    return 0;
                }else if(o1.lectures == 0){
                    return 1;
                }else if(o2.lectures == 0){
                    return -1;
                }else {
                    return (int)(o2.sadness-o1.sadness);
                }
            }
        });
        Long totalSadness = 0L;
        for(int i =1; i<=days; i++){
            Iterator<Trainer> iterators = listDays[i].iterator();
            while (iterators.hasNext()){
                Trainer trainer = iterators.next();
                queue.offer(trainer);
            }
            if(!queue.isEmpty()){
                Trainer trainer = queue.poll();
                trainer.lectures--;
                if(trainer.lectures>0){
                    queue.offer(trainer);
                }
            }
        }
        while (!queue.isEmpty()){
            Trainer trainer = queue.poll();
            totalSadness += trainer.sadness*trainer.lectures;
        }
        System.out.println(totalSadness);
    }

}
