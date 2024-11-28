from multiprocessing import Queue
from multiprocessing import Process

def collatz_check(n):
    while n != 1:
        if n % 2 == 0:
            n //= 2
        else:
            n = 3 * n + 1
    return True

def collatz_in_range(start, end, result_queue):
    truth_collector=0
    for i in range(start, end):
        if collatz_check(i):
            truth_collector+=1
    result_queue.put(truth_collector)
    

if __name__ == "__main__":

    result_queue = Queue()

    process_1 = Process(target=collatz_in_range, args=(1, 250000000, result_queue))
    process_2 = Process(target=collatz_in_range, args=(250000000, 500000000, result_queue))
    process_3 = Process(target=collatz_in_range, args=(500000000, 750000000, result_queue))
    process_4 = Process(target=collatz_in_range, args=(750000000, 1000000000, result_queue))
    
    process_1.start()
    process_2.start()
    process_3.start()
    process_4.start()

    process_1.join()
    process_2.join()
    process_3.join()
    process_4.join()

    total=0
    while not result_queue.empty():
        truth_collector=result_queue.get()
        total+=truth_collector
    
    print(total)
    if total==999999999:
        print("Гипотеза Коллатца подтверждена для всех чисел в диапазоне.")
    else: 
        print("Гипотеза Коллатца не подтверждена для всех чисел в диапазоне.")
  