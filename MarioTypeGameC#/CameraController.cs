using System.Numerics;
using System.Transactions;
using System;
using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class CameraController : MonoBehaviour
{
    [SerializeField] private Transform player;
    // Update is called once per frame
    private void Update()
    {
       transform.position = new UnityEngine.Vector3(player.position.x, player.position.y, transform.position.z);  
    }
}
