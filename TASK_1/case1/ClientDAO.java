package TASK_1.case1;

import TASK_1.shared.Client;

import java.util.List;

public interface ClientDAO
{
    void init();
    void addClient(String name, int age);
    void deleteClient(int id);
    List<Client> getAll();
    long count();
}
