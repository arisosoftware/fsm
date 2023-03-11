import queue
import random
import matplotlib.pyplot as plt

class Car:
    def __init__(self, make, model):
        self.make = make
        self.model = model
        self.battery_level = 100
    
    def open_battery_compartment(self):
        # simulate opening the battery compartment
        print(f"{self.make} {self.model}: Opening battery compartment...")
    
    def close_battery_compartment(self):
        # simulate closing the battery compartment
        print(f"{self.make} {self.model}: Closing battery compartment...")
    
    def swap_battery(self):
        # simulate swapping the battery
        print(f"{self.make} {self.model}: Swapping battery...")
        self.battery_level = 100
        print(f"{self.make} {self.model}: Battery swap complete. Battery level: {self.battery_level}%")
        
class ChargingStation:
    def __init__(self, num_batteries):
        self.num_batteries = num_batteries
        self.waiting_cars = queue.Queue()
        self.wait_times = []
        self.sim_time = 0
    
    def add_car_to_queue(self, car):
        # add the car to the waiting queue
        self.waiting_cars.put(car)
        print(f"{car.make} {car.model} added to waiting queue.")
    
    def process_queue(self):
        # process the waiting queue
        while not self.waiting_cars.empty():
            car = self.waiting_cars.get()
            print(f"{car.make} {car.model} is next in line.")
            car.open_battery_compartment()
            car.swap_battery()
            car.close_battery_compartment()
            self.wait_times.append(self.sim_time - car.arrival_time)
    
    def run(self, num_cars, duration):
        # simulate the charging station running
        end_time = duration
        while self.sim_time < end_time:
            # generate a random number of cars to add to the queue
            for i in range(num_cars):
                make = random.choice(["Tesla", "Nissan", "Chevrolet", "BMW"])
                model = random.choice(["Model S", "Leaf", "Bolt", "i3"])
                car = Car(make, model)
                car.arrival_time = self.sim_time
                self.add_car_to_queue(car)
            
            # process the waiting queue
            self.process_queue()
            
            # increment the simulated time
            self.sim_time += 10
            
        # plot the average wait time for different numbers of batteries
        avg_wait_times = []
        for i in range(1, self.num_batteries+1):
            wait_times_for_num_batteries = [wait_time for wait_time in self.wait_times if wait_time < i*10]
            avg_wait_time = sum(wait_times_for_num_batteries) / len(wait_times_for_num_batteries) if wait_times_for_num_batteries else 0
            avg_wait_times.append(avg_wait_time)
        plt.plot(range(1, self.num_batteries+1), avg_wait_times)
        plt.xlabel("Number of batteries")
        plt.ylabel("Average wait time (seconds)")
        plt.title(f"Average wait time for {num_cars} cars over {duration} seconds")
        plt.show()

# create a charging station with 10 batteries and run it
charging_station = ChargingStation(num_batteries=10)
charging_station.run(num_cars=10, duration=3600)



# 故剑情深，南园遗梦。写一首长诗歌吧

# 清风秋月悠悠，独立南园思故友。
# 往事如风过眼前，情深义重绕心间。
# 剑胆琴心俱灵气，英姿飒爽神采奕。
# 豪气冲天凌云去，魂归故园伴月华。
# 纵有万千思绪乱，仍愿情深不变数。
# 倚栏幽怀百感生，长歌饮酒怀念情。
# 南园遗梦难忘怀，人生若只如初见。

